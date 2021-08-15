package mvc.controller;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.command.CommandHandler;
import member.command.NullHandler;




//이 Servlet이 흐름 통제
public class ControllerUsingURI extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Map<String, CommandHandler> commandHandlerMap = new HashMap<String, CommandHandler>();
	
    @Override
    public void init() throws ServletException {
    	System.out.println("ControllerUsingFile 서블릿 동작");
    	String configFile = getInitParameter("configFile");
    	System.out.println(configFile);
    	Properties prop = new Properties();
    	String configFilePath = getServletContext().getRealPath(configFile);
    	System.out.println(configFilePath);
    	try(FileReader fis = new FileReader(configFilePath)) {
    		prop.load(fis);
    	}catch(IOException e) {
    		throw new ServletException();
    	}
    	Iterator<Object> keyIter= prop.keySet().iterator();
    	while(keyIter.hasNext()) {
    		String command = (String)keyIter.next();
    		System.out.println(command);
    		String  handlerClassName = prop.getProperty(command);	//키에 맞는 값을 가져옴
    		System.out.println("값:"+handlerClassName);
    		
    		try {
    			Class<?> handlerClass= Class.forName(handlerClassName);
    			CommandHandler handlerInstance= (CommandHandler) handlerClass.newInstance();
    			commandHandlerMap.put(command, handlerInstance);	//결국 모든 것은 commandHandlerMap에 command(ex./join.do)랑 핸들러를 넣기 위해.
    		}catch(ClassNotFoundException | InstantiationException | IllegalAccessException e) {
    			throw new ServletException(e);
    		}
    	}
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		process(request, response);
	}

	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		process(request, response);
	}
	
	private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String command = request.getRequestURI();		//request객체에서 uri가져온다. form action=join.do-->이게 command이자 uri인 듯.
		
		if(command.indexOf(request.getContextPath())==0 ) {	//contextPath랑 일치하는 문자열 어디 index부터 시작하는지.
			command = command.substring(request.getContextPath().length());	//substring인자의 index부터 잘라서 command에 담는다.
			
		}
		System.out.println("--->"+command);
		CommandHandler handler = commandHandlerMap.get(command);
		if(handler==null) {
			handler = new NullHandler(); 
			
		}
		String viewPage= null;
		try {
			viewPage = handler.process(request, response);		//handler.process(request, response)여기에서 많은 일들이 일어난다. (모델로 넘어가는 인터페이스 역할)
		}catch(Exception e) {
			throw new ServletException();
		}
		if(viewPage!=null) {
			RequestDispatcher dispatcher= request.getRequestDispatcher(viewPage);	//주어진 viewPage를 향해 resource(request)를 전달하기 위햔 wrapper역할 객체 생성
			dispatcher.forward(request, response);	//Forwards a request froma servlet to another resource 
													//(servlet, JSP file, orHTML file) on the server
		}											//jsp:forward역할을 하는 것 같다.
	}
}
