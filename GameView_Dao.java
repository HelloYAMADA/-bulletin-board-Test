package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Beans.gtitle_beans;

public class GameView_Dao {
	  // データベース接続に使用する情報
	  private final String JDBC_URL = "jdbc:mariadb://localhost/threadsystemdb";
	  private final String DB_USER = "root";
	  private final String DB_PASS = "mysql";
	  
	//DBにアクセスするためのもろもろ
	public GameView_Dao(){
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
	public List<gtitle_beans> game_tget() {
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			//ゲームビューからタイトルID抜き出し
	    	String sql = "SELECT GAME_ID, GAME_TITLE FROM gameinf_table WHERE GAME_DEL = 0";
	    	
		    PreparedStatement pStmt = conn.prepareStatement(sql);
		    
		    //実行
		  	ResultSet rs = pStmt.executeQuery();  

		    List<gtitle_beans> titlelist = new ArrayList<>();
		    //Listにセレクト文でゲットしてきた内容を追加
		    while(rs.next()) {
		    	int ID = rs.getInt("GAME_ID");
		    	String title  = rs.getString("GAME_TITLE");
		    	gtitle_beans bean = new gtitle_beans(ID,title);
		    	titlelist.add(bean);
		    }
		    return titlelist;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	
}
