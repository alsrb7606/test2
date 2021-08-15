package article.command;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import article.service.ArticleContentNotFoundException;
import article.service.ArticleData;
import article.service.ArticleNotFoundException;
import article.service.ReadArticleService;
import member.command.CommandHandler;
public class ReadArticleHandler implements CommandHandler{
	private ReadArticleService readService = new ReadArticleService();
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String noVal = request.getParameter("no");
		int articleNum = Integer.parseInt(noVal);
		try {
			ArticleData articleData = readService.getArticle(articleNum, true);
			request.setAttribute("articleData", articleData);
			return "/WEB-INF/view/readArticle.jsp";
		}catch(ArticleNotFoundException | ArticleContentNotFoundException e) {
			request.getServletContext().log("no artice",e);
			response.sendError(HttpServletResponse.SC_NOT_FOUND);		// response객체에 404에러보내기 (클라이언트한테)
			return null;
		}
		
	}

}
