package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import beans.mem_beans;

public class Mem_Dao {
	
	  // データベース接続に使用する情報
	  private final String JDBC_URL = "jdbc:mariadb://localhost/threadsystemdb";
	  private final String DB_USER = "root";
	  private final String DB_PASS = "mysql";
	  
	//DBにアクセスするためのもろもろ
	public Mem_Dao(){
		   // JDBCドライバを読み込む
		try {
	        Class.forName("org.mariadb.jdbc.Driver");
	        System.out.println("JDBCドライバ読み込みOK");
		} catch (ClassNotFoundException e) {
	        throw new IllegalStateException("JDBCドライバを読み込めませんでした");
	    }
	}
	//ログイン機能のメソッド
	//daoでログインできるか判断
	//ユーザー名取得は一旦放置で
	//戻り値beans変更
	public mem_beans  Mem_log(String num, String pass) {
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
	    	String sql = "SELECT STU_PASS, USER_NAME FROM stuinf_table WHERE STU_ID = ?";
	    	mem_beans bean = new mem_beans();
	    	
		    PreparedStatement pStmt = conn.prepareStatement(sql);
		    
		    pStmt.setString(1, num);
		    
		    //実行
		  	ResultSet rs = pStmt.executeQuery();  
		  	
		  	//パスチェック
		  	String passcheck;
		  	String name;
		    if(rs.next()) {
		    	passcheck  = rs.getString("STU_PASS");
		    	name = rs.getString("USER_NAME");
		    	//仮で表示本番ではこの分は表示させない
		    	System.out.println("パスわーど：" + passcheck);	
		    	if(passcheck.equals(pass)) {
		    		System.out.println("パスワード一致認証成功");
		    		//beansにセット
		    		bean.setMem_id(num);
		    		bean.setUser_name(name);
		    		return bean;
		    		
		    	}else {
		    		System.out.println("パスワード不一致認証失敗");
		    	}
		    }else {
		        System.out.println("該当の学籍番号が見つからないためパスチェックができませんでした。");
		    }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null;
	    }
		return null;
	}
}
