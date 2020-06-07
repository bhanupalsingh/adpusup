package com.example.adpushup.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;
public class MysqlConnector {
	
	
	//we can store these info in environment path or  properties files . for simplicity I am intializing directly here only. 
	private static final String USERNAME =  "traform"; //"sql12346031" ;
	private static final String PASSWORD = "traform"; //"s5iiu3WkCq";
	private static final String DATABASE = "adpushup"; //"sql12346031";
	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";//"com.mysql.jdbc.Driver";  
	static final String JDBC_DB_URL =  "jdbc:mysql://localhost:3306/"+DATABASE;          //"jdbc:mysql://sql12.freesqldatabase.com:3306/"+DATABASE;
	private static GenericObjectPool gPool = null;
	public static DataSource poolingDataSource ;
	private static MysqlConnector mysqlConnector ;
	
	
	
	private MysqlConnector() {
		try {
			poolingDataSource = setUpPool();
			printDbStatus();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static MysqlConnector getInstance() {
		if(mysqlConnector == null) {
			mysqlConnector = new MysqlConnector();
		}
		
		return mysqlConnector;
	}
	
	public Connection getConnection() {
		try {
			return poolingDataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
    @SuppressWarnings("unused")
    public DataSource setUpPool() throws Exception {
        Class.forName(JDBC_DRIVER);
        gPool = new GenericObjectPool();
        gPool.setMaxActive(20);
 
        ConnectionFactory cf = new DriverManagerConnectionFactory(JDBC_DB_URL, USERNAME, PASSWORD);
        PoolableConnectionFactory pcf = new PoolableConnectionFactory(cf, gPool, null, null, false, true);
        return new PoolingDataSource(gPool);
    }
 
    public GenericObjectPool getConnectionPool() {
        return gPool;
    }
 
    private void printDbStatus() {
        System.out.println("Max.: " + getConnectionPool().getMaxActive() + "; Active: " + getConnectionPool().getNumActive() + "; Idle: " + getConnectionPool().getNumIdle());
    }
 
    
}
