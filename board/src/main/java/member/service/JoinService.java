package member.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import member.dao.MemberDao;
import member.model.Member;

public class JoinService {	//JoinHandler를 수행하기 위한 서비스 (JoinService)
	private MemberDao memberDao = new MemberDao();
	
	public void join(JoinRequest joinReq) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();	//여기서 미리 Connection객체 만들어서 밑에서 사용한다. 
			conn.setAutoCommit(false);
			Member member = memberDao.selectById(conn, joinReq.getId());
			if(member!=null) {
				JdbcUtil.rollback(conn);
				throw new DuplicateIdException();
			}
			memberDao.insert(conn, new Member(
									joinReq.getId(),
									joinReq.getName(),
									joinReq.getPassword(),
									new Date())																	
									);
			conn.commit();			//다 수행되면 commit
		}catch(SQLException e) {
			JdbcUtil.rollback(conn);		//아니면 rollback
			throw new RuntimeException(e);
		}finally {
			JdbcUtil.close(conn);		//Connection close한다.
		}
		
	}
}
