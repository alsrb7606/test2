package article.service;

import java.sql.Connection;

import article.dao.ArticleContentDao;
import article.dao.ArticleDao;

public class DeleteArticleService {
	ArticleContentDao articleContentDao = new ArticleContentDao();	//dao객체 2개 생성
	ArticleDao articleDao = new ArticleDao();
	
	public boolean delete(Connection conn, int no) {
		articleContentDao.delete(conn, no);		//dao객체 이용하여 삭제기능 수행
		articleDao.delete(conn, no);		
		return true;		//삭제 되었으면 true		
	}
}
