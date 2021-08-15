package article.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import article.dao.ArticleContentDao;
import article.dao.ArticleDao;
import article.model.Article;
import article.model.ArticleContent;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class WriteArticleService {
	private ArticleDao articleDao = new ArticleDao();
	private ArticleContentDao contentDao = new ArticleContentDao();
	
	public Integer write(WriteRequest req) {
		Connection conn = null;
		try {
			System.out.println("여기들어옴4");
			conn= ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			Article article = toArticle(req);		//WriteRequest객체를 통해 일단 Article객체 만든다.
			Article savedArticle = articleDao.insert(conn, article);	//dao(insert)작업을 거친 savedArticle
			if(savedArticle==null) {
				throw new RuntimeException("fail to insert article");
			}
			ArticleContent content = new ArticleContent(
					savedArticle.getNumber(),
					req.getContent());
			ArticleContent savedContent = contentDao.insert(conn, content);
			if(savedContent==null) {
				throw new RuntimeException("fail to insert article_content");
			}
			conn.commit();
			
			return savedArticle.getNumber();
					
		}catch(SQLException e){
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		}catch(RuntimeException e){
			JdbcUtil.rollback(conn);
			throw e;
		}finally {
			JdbcUtil.close(conn);
		}
		
	}

	private Article toArticle(WriteRequest req) {
		Date now = new Date();
		return new Article(null, req.getWriter(), req.getTitle(), now, now, 0);
	}
}
