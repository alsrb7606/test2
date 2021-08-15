package member.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NullHandler implements CommandHandler{
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("명령어가 없습니다.");
		return "/chapter18/hello.jsp";
	}
		
	
}
