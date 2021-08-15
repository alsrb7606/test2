package article.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import article.model.Article;
import article.model.Writer;
import jdbc.JdbcUtil;

public class ArticleDao {
	public Article insert(Connection conn, Article article) throws SQLException {
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			System.out.println("여기들어옴7");
			pstmt = conn.prepareStatement("insert into article values(num_seq.nextval, ?,?,?,?,?,0)");	
			
		pstmt.setString(1, article.getWriter().getId());	
		pstmt.setString(2, article.getWriter().getName());
		pstmt.setString(3, article.getTitle());
		pstmt.setTimestamp(4, toTimestamp(article.getRegDate()));
		pstmt.setTimestamp(5, toTimestamp(article.getModifiedDate()));
		int insertedCount = pstmt.executeUpdate();
		System.out.println("여기들어옴8");
		if(insertedCount>0) {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select max(article_no) from article"); 	//select last_insert_id() from article 
			if(rs.next()) {
				Integer newNum =rs.getInt(1);	//가장 마지막 글번호 구하기
				return new Article(newNum,
						article.getWriter(), 
						article.getTitle(), 
						article.getRegDate(), 
						article.getModifiedDate(), 
						0);
			}
		}
		return null;
						
		}finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
			JdbcUtil.close(pstmt);
			
		}
		
		
		
	}

	private Timestamp toTimestamp(Date date) {
		return new Timestamp(date.getTime());
		
	}
	
	public int selectCount(Connection conn) throws SQLException {
		Statement stmt= null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(*) from article");
			if(rs.next()) {
				return rs.getInt(1);
			}
			return 0;
		}finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}
	}
	
	public List<Article> select(Connection conn, int startRow, int size) throws SQLException {
		System.out.println("여기!!!!---1");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			System.out.println("here!!!!--3");
			pstmt = conn.prepareStatement("select * from (select rownum rnum, article_no, writer_id, writer_name, title, regdate, moddate, read_cnt from article where "
					+ "rownum>=? order by article_no desc) where rnum<=? ");
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, size);

			rs= pstmt.executeQuery();
			System.out.println("here!!!!--4");
			List<Article> result = new ArrayList<>();
			while(rs.next()) {
				result.add(convertArticle(rs));
			}
			
			return result;
		}finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		
		
	}

	private Article convertArticle(ResultSet rs) throws SQLException {	//여기서 튜플(rs)을 객체데이터로 옮겨준다.
		
		return new Article(rs.getInt("article_no"),
				new Writer(
						rs.getString("writer_id"),
						rs.getString("writer_name")),
						rs.getString("title"),
						toDate(rs.getTimestamp("regdate")),
						toDate(rs.getTimestamp("moddate")),
						rs.getInt("read_cnt"));
				
	}

	private Date toDate(Timestamp timestamp) {
												//long 형태를 매개로 바뀌는 듯.
		return new Date(timestamp.getTime());
	}
	
	public Article selectById(Connection conn, int no) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from article where article_no = ?");
			pstmt.setInt(1, no);
			rs =pstmt.executeQuery();
			Article article = null;
			if(rs.next()) {
				article = convertArticle(rs);
			}
			return article;
		}finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}	
	}
	
	public void increaseReadCount(Connection conn, int no) throws SQLException {
		try (PreparedStatement pstmt = 
				conn.prepareStatement("update article set read_cnt = read_cnt+1 where article_no=?")) {
			pstmt.setInt(1, no);
			pstmt.executeUpdate();
		}
			
		
	}
	
	public int update(Connection conn, int no, String title)throws SQLException {
		try(PreparedStatement pstmt = conn.prepareStatement("update article set title=?, moddate=now()"+
				" where article_no=?")) {
			pstmt.setString(1, title);
			pstmt.setInt(2, no);
			return pstmt.executeUpdate();
		}		
	}

	public void delete(Connection conn, int no) {
		try(PreparedStatement pstmt = conn.prepareStatement("delete from Article where article_no=?")) {
			pstmt.setInt(1, no);	//?에 no값 넣기
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException();	//runtimeException상속받아서 새로운 exception생각
		}
		
	}
	
	
	
	
		
}
