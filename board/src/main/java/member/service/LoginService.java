package member.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.connection.ConnectionProvider;
import member.dao.MemberDao;
import member.model.Member;

public class LoginService {
	private MemberDao memberDao = new MemberDao();
	
	public User login(String id, String password) {
		try(Connection conn = ConnectionProvider.getConnection()) {		//일단 db에 접근하려면 Connection필요.connection가지고 memberDao객체사용.
			Member member = memberDao.selectById(conn, id);
			if(member==null) {
				throw new LoginFailException();
			}
			if(!member.matchPassword(password)) {
				throw new LoginFailException();
			}
			return new User(member.getId(), member.getName());	
		}catch(SQLException e) {		//원래 SQLException
			System.out.println("exception!!!------");
			throw new RuntimeException(e);			//RuntimeException으로 throw
		}
				
	}
}
