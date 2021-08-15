package member.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutHandler implements CommandHandler{

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(false);
		if(session!=null) {
			session.invalidate();		//세선 무효화
		}
		response.sendRedirect(request.getContextPath()+"/index.jsp");		//jsp redirect기능
		return null;		//controlloer에 viewpage를 null로 반환하고 바로 redirect해서 요청을 response객체와 함께 다시 새로운 주소로 보내준다.
	}

}
