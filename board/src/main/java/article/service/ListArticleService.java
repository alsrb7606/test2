package article.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import article.dao.ArticleDao;
import article.model.Article;
import jdbc.connection.ConnectionProvider;

public class ListArticleService {
	private ArticleDao articleDao = new ArticleDao();
	private int size=10;
	public ArticlePage getArticlePage(int pageNum) {
		try(Connection conn = ConnectionProvider.getConnection()) {
			int total = articleDao.selectCount(conn);
			System.out.println("here!!!!--1");
			List<Article> content = articleDao.select(conn, (pageNum-1)*size, size);
			System.out.println("here!!!!--2");
			return new ArticlePage(total, pageNum, size, content);
			
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
}
