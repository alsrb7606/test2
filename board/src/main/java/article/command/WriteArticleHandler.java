package article.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import article.model.Writer;
import article.service.WriteArticleService;
import article.service.WriteRequest;
import member.command.CommandHandler;
import member.service.User;

public class WriteArticleHandler implements CommandHandler{
	private static final String FORM_VIEW = "/WEB-INF/view/newArticleForm.jsp";
	private WriteArticleService writeService = new WriteArticleService();
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(request.getMethod().equalsIgnoreCase("GET")) {
			return processFrom(request, response);
		}else if(request.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(request, response);
		}else {
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
		
	}
	private String processSubmit(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Boolean> errors = new HashMap<>();
		request.setAttribute("errors", errors);
		System.out.println("여기들어옴1");
		User user = (User)request.getSession(false).getAttribute("authUser");
		WriteRequest writeReq = createWriteRequest(user, request);
		writeReq.validate(errors);
		if(!errors.isEmpty()) {
			return FORM_VIEW;
		}
		System.out.println("여기들어옴2");
		int newArticleNo = writeService.write(writeReq);
		System.out.println("여기들어옴3");
		request.setAttribute("newArticleNo", newArticleNo);
		
		return "/WEB-INF/view/newArticleSuccess.jsp";
	}
	
	private String processFrom(HttpServletRequest request, HttpServletResponse response) {
		
		return FORM_VIEW;
	}

	
	
	private WriteRequest createWriteRequest(User user, HttpServletRequest request) {
		return new WriteRequest(new Writer(user.getId(), user.getName()), request.getParameter("title"), request.getParameter("content"));
		
	}
}
