package jdbc;

import java.io.IOException;
import java.io.StringReader;
import java.sql.DriverManager;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDriver;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;


public class DBCPInitListener implements ServletContextListener {

   //프로퍼티(property)에 모든 정보 넣어놓고 사용.
	public void contextInitialized(ServletContextEvent arg0)  { 
        String poolConfig = arg0.getServletContext().getInitParameter("poolConfig");
        System.out.println("poolConfig"+poolConfig);
        Properties prop = new Properties();
        try {
			prop.load(new StringReader(poolConfig));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
        loadJDBCDriver(prop);
        initConnectionPool(prop);
	}
	
	
    private void loadJDBCDriver(Properties prop) {
    	
   
    	String driverClass = prop.getProperty("jdbcdriver");
    	try {
			Class.forName(driverClass);
			System.out.println("jdbc드라이버 로딩 성공");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("fail to load JDBC Driver");
		}
    }	
    	
    
	private void initConnectionPool(Properties prop) {
		try{ 
		String url=prop.getProperty("jdbcUrl");
		String user=prop.getProperty("dbUser");
		String pass=prop.getProperty("dbPass");
		ConnectionFactory connFactory = 
				 new DriverManagerConnectionFactory(url, user, pass);

		PoolableConnectionFactory poolableConnFactory = 
				new PoolableConnectionFactory(connFactory, null);
		poolableConnFactory.setValidationQuery("select 1");	//유효한지 검사
		String validationQuery = prop.getProperty("validationQuery");
		if(validationQuery!=null&& !validationQuery.isEmpty()) {
			poolableConnFactory.setValidationQuery(validationQuery);
		}
		
		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		poolConfig.setTimeBetweenEvictionRunsMillis(1000L * 60L * 5L);
		poolConfig.setTestWhileIdle(true);
		
		int mindle = getIntProperty(prop, "mindle", 5);
		poolConfig.setMinIdle(mindle);
		int maxTotal = getIntProperty(prop, "maxTotal", 50);
		poolConfig.setMaxTotal(maxTotal);

		GenericObjectPool<PoolableConnection> connectionPool = 
				new GenericObjectPool<>(poolableConnFactory, poolConfig);
		poolableConnFactory.setPool(connectionPool);
		
		Class.forName("org.apache.commons.dbcp2.PoolingDriver");
		PoolingDriver driver = 
				(PoolingDriver) DriverManager.getDriver("jdbc:apache:commons:dbcp:");
		String poolName = prop.getProperty("poolName");
		driver.registerPool(poolName, connectionPool);
		System.out.println("풀링 드라이버");
	} catch (Exception e) {
		throw new RuntimeException(e);
	}
		
	}
	private int getIntProperty(Properties prop, String propName, int defaultValue) {
		String value = prop.getProperty(propName);
		if(value==null) return defaultValue;
		return Integer.parseInt(value);
	}
	
	
	public void contextDestroyed(ServletContextEvent arg0)  { 
         
    }

	
    
	
}
