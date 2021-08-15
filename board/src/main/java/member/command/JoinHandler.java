package member.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.service.DuplicateIdException;
import member.service.JoinRequest;
import member.service.JoinService;

public class JoinHandler implements CommandHandler{		//MVC의 모델(기능처리)역할.
	private static final String FORM_VIEW ="/WEB-INF/view/joinForm.jsp";
	private JoinService joinService = new JoinService();
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {	//처리할 꺼 하고 view페이지를 반환한다.
		if(request.getMethod().equalsIgnoreCase("GET")) {
			return processForm(request, response);
		} else if(request.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(request, response);		//post일 때 제대로 처리하는 듯.processSubmit메소드로.
		} else {
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		}
		return null;
	}
	
	
	private String processForm(HttpServletRequest request, HttpServletResponse response) {
		return FORM_VIEW;
	}
	
	private String processSubmit(HttpServletRequest request, HttpServletResponse response) {
		JoinRequest joinReq = new JoinRequest();
		joinReq.setId(request.getParameter("id"));
		joinReq.setName(request.getParameter("name"));
		joinReq.setPassword(request.getParameter("Password"));
		joinReq.setConfirmPassword(request.getParameter("confirmPassword"));
		
		Map<String, Boolean> errors = new HashMap<>(); 
		request.setAttribute("errors", errors);
		
		joinReq.validate(errors);
		
		if(!errors.isEmpty()) {
			return FORM_VIEW;
		}
		
		try {
			joinService.join(joinReq);		//exception안나서 잘 수행되면 return joinSuccess.jsp
			return "/WEB-INF/view/joinSuccess.jsp";
		}catch(DuplicateIdException e) {
			errors.put("duplicateId", Boolean.TRUE);
			return FORM_VIEW;
		}
		
	}

}
