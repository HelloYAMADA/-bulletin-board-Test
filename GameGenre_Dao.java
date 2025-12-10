package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Beans.genre_beans;

public class GameGenre_Dao {
	  // データベース接続に使用する情報
	  private final String JDBC_URL = "jdbc:mariadb://localhost/threadsystemdb";
	  private final String DB_USER = "root";
	  private final String DB_PASS = "mysql";
	  
	//DBにアクセスするためのもろもろ
	public GameGenre_Dao(){
		   // JDBCドライバを読み込む
		try {
	        Class.forName("org.mariadb.jdbc.Driver");
	        System.out.println("JDBCドライバ読み込みOK");
		} catch (ClassNotFoundException e) {
	        throw new IllegalStateException("JDBCドライバを読み込めませんでした");
	    }
	}
	
	//ゲームジャンル一覧取得するdaoとかゲームタイトル一覧取得するdaoとかいろんなのが入る予定
	//beensがないんで戻り値一旦なしで
	public List<Beans.genre_beans> game_get() {
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			//ゲームビューからタイトルID抜き出し
	    	String sql = "SELECT GENRE_ID, GENRE_NAME FROM GAMEGEN_TABLE";
	    	
		    PreparedStatement pStmt = conn.prepareStatement(sql);
		    
		    //実行
		  	ResultSet rs = pStmt.executeQuery();  
		  	
		    List<genre_beans> genrelist = new ArrayList<>();
		    //Listにセレクト文でゲットしてきた内容を追加
		    while(rs.next()) {
		    	int ID = rs.getInt("GENRE_ID");
		    	String name  = rs.getString("GENRE_NAME");
		    	genre_beans bean = new genre_beans(ID,name);
		    	genrelist.add(bean);
		    }
		    return genrelist;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
}
