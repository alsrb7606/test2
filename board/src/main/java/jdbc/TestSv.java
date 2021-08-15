package jdbc;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class TestSv extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   @Override
   public void init() throws ServletException {
	   System.out.println("서블릿 초기화");
   }
   
   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
	   process(req, resp);
   }
   
   
   @Override
   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	   process(req, resp);
   }

   
   private void process(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	   
	   
	   
	   
	   resp.setContentType("text/html;charset=utf-8");
	   PrintWriter pw= resp.getWriter();
	   pw.print("abcd<br>");
	   pw.print("안녕하세요<br>");
	   
   }

}
