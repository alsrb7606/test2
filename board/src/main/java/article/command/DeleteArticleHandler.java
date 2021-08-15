package article.command;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import article.model.Article;
import article.service.ArticleData;
import article.service.DeleteArticleService;
import article.service.ReadArticleService;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import member.command.CommandHandler;
import member.service.User;

public class DeleteArticleHandler implements CommandHandler{
	//delete서비스객체
	DeleteArticleService deleteService = new DeleteArticleService();
	ReadArticleService readService = new ReadArticleService();
	private static final String FORM_VIEW="/WEB-INF/view/readArticle.jsp";
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {				
		if(request.getMethod().equalsIgnoreCase("GET")) {
			System.out.println("get까지 넘어옴");
			return processGet(request, response);
			
		}else if(request.getMethod().equalsIgnoreCase("POST")) {
			return processPost(request, response);
		}else {
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}		
	}

	private String processPost(HttpServletRequest request, HttpServletResponse response) {//post처리
		return FORM_VIEW;		
	}
	
	private String processGet(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {//get처리
		Connection conn =null;
		conn= ConnectionProvider.getConnection();
		int no = Integer.parseInt(request.getParameter("no"));	//파라미터 no값 가져오기
		User user = (User)request.getSession().getAttribute("authUser");  //--> ㅁAuthuser객체 
		
		ArticleData articleData = readService.getArticle(no, false);
		
		//삭제할 수 있는 권한인지 체크
		if(!canDelete(user, articleData)) {			
			System.out.println("삭제할 권한이 없습니다.");
			response.sendError(HttpServletResponse.SC_FORBIDDEN);	//response에 응답추가.
		}else {			
			deleteService.delete(conn, no);		//삭제 수행
			System.out.println("삭제 되었습니다.");
			conn.commit();
			JdbcUtil.close(conn);
			return "/WEB-INF/view/deleteSuccess.jsp";
		}
		return null;	
	}

	boolean canDelete(User authUser, ArticleData articledata) {
		String user=articledata.getArticle().getWriter().getId();
		return authUser.getId().equals(user);
	}
}
