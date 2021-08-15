package member.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import member.dao.MemberDao;
import member.model.Member;

public class ChangePasswordService {
	private MemberDao memberDao = new MemberDao();
	public void changePassword(String userId, String curPwd, String newPwd) {
		Connection conn=null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			Member member = memberDao.selectById(conn, userId);
			if(member==null) {
				throw new MemberNotFoundException();
			}
			if(!member.matchPassword(curPwd)) {
				System.out.println("member의 password"+member.getPassword());
				System.out.println("새로운 비밀번호:"+newPwd);
				System.out.println("invalidpasswordException");
				throw new InvalidPasswordException();
				
			}
			member.changePassword(newPwd);
			memberDao.update(conn, member);
			conn.commit();
			
		}catch(SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
		
		
		
	}
}
