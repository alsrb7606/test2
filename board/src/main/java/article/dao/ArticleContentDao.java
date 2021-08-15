package article.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import article.model.ArticleContent;
import jdbc.JdbcUtil;

public class ArticleContentDao {
	public ArticleContent insert(Connection conn, ArticleContent content) throws SQLException {
		PreparedStatement pstmt = null;
		try {
		pstmt = conn.prepareStatement("insert into article_content"+" (article_no, content) values(?,?)");
		pstmt.setLong(1, content.getNumber());
		pstmt.setString(2, content.getContent());
		int insertedCount = pstmt.executeUpdate();
		if(insertedCount>0) {
			return content;
		}else {
			return null;
		}
		} finally {
			JdbcUtil.close(pstmt);
		}
				
	}
	
	public ArticleContent selectById(Connection conn, int no)throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from article_content where article_no=?");
			pstmt.setInt(1, no);
			rs= pstmt.executeQuery();
			ArticleContent content = null;
			if(rs.next()) {
				content = new ArticleContent(rs.getInt("article_no"), rs.getString("content"));
			}
			return content;
		}finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		
	
	}
	
	public int update(Connection conn, int no, String content)throws SQLException {
		try(PreparedStatement pstmt = conn.prepareStatement("update article_content set content=? where article_no=?")) {
			pstmt.setString(1, content);
			pstmt.setInt(2, no);
			return pstmt.executeUpdate();
		}		
	}

	public void delete(Connection conn, int no) {
		try(PreparedStatement pstmt = conn.prepareStatement("delete from Article_content where article_no=?")) {
			pstmt.setInt(1, no);	//?에 no값 넣기
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException();	//runtimeException상속받아서 새로운 exception생각
		}
		
	}
	
}
