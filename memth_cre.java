package dao;

//import
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
import java.sql.Statement;

public class memth_cre {
	  // データベース接続に使用する情報
	  private final String JDBC_URL = "jdbc:mariadb://localhost/threadsystemdb";
	  private final String DB_USER = "root";
	  private final String DB_PASS = "mysql";
	  
	//DBにアクセスするためのもろもろ
	public memth_cre(){
		   // JDBCドライバを読み込む
		try {
	        Class.forName("org.mariadb.jdbc.Driver");
	        System.out.println("JDBCドライバ読み込みOK");
		} catch (ClassNotFoundException e) {
	        throw new IllegalStateException("JDBCドライバを読み込めませんでした");
	    }
	}
	
	//getStringするには.nextが必要注意されたし
	public void test() {
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			
			//成功したかどうかをサーブレットに返す
			String  name;
			System.out.println("OKOK");
	    	String sqlref = "SELECT NAME FROM stuinf_table WHERE STU_ID = 20234106";
		    PreparedStatement pStmtref = conn.prepareStatement(sqlref);
		    ResultSet rsref = pStmtref.executeQuery();
		    if(rsref.next()) {
		    	name = rsref.getString("NAME");
		    	System.out.println(name);	
		    }else {
		        System.out.println("データが見つかりませんでした。");
		    }

	    	
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public boolean memth_method(String title, String gatitle, String gajanre, String cont)throws SQLException{
	    // データベース接続
	    try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			//DB登録する内容をsqlに埋め込む
			//gameID取得内容がゲームタイトルとゲームジャンルだからそこからとってくりゃええんか？

	    	//会員IDが謎
	    	//String val = request.getParameter("");
	    	//ビューの名前がわかんないよ～
	    	//いったんテーブルに何でもいいから作れるようにしよう
	    	//"SELECT GAME_ID FROM ビューの名前を入力 WHERE GAME_TITLE = ? AND GENRE_NAME = ?"; 
	    	String sqlref = "SELECT GAME_ID FROM gameinf_table WHERE GAME_TITLE = ?";
	    	//POS_LIM,THREAD_DELはデフォが入るって仕様書には書かれてたから消すかも
	    	//スレッドタイトル、会員ID、ゲームID、通報回数、作成日、最終更新日、コメント数
			String sqlinf = "INSERT INTO THINF_TABLE(THREAD_TITLE,STU_ID,GAME_ID,REPORT,CRE_DATE,UPD_DATE,COM_COUNT,POS_LIM,THREAD_DEL) "
					+ "VALUES(?,20234106,?,0,?,?,1,false,false)";
			//学籍番号を仮で入れておく
			String sqlcont = "INSERT INTO THCONT_TABLE "
					+ "VALUES(?,1,20234106,?) ";
		    PreparedStatement pStmtref = conn.prepareStatement(sqlref);
		    PreparedStatement pStmtinf = conn.prepareStatement(sqlinf,Statement.RETURN_GENERATED_KEYS);
		    PreparedStatement pStmtcont = conn.prepareStatement(sqlcont);
			//スレッド情報テーブル作成後スレッド内容テーブル作成
			
		      // INSERT文中の「?」に使用する値を設定しSQLを完成
		    //げーむID 取得
		    pStmtref.setString(1, gatitle);
		    //いったんこいつ消すビューの作成状況に合わせてふくげんしてくれ
		    //pStmtref.setString(2, gajanre);
		    
		  	ResultSet rsref = pStmtref.executeQuery();  
		    int gaID;
		    if(rsref.next()) {
		    	gaID  = rsref.getInt("GAME_ID");
		    	System.out.println(gaID);	
		    }else {
		        System.out.println("ゲームIDが見つかりませんでした。");
		        gaID = 0;
		    }
		    
		    //スレッド情報		    
		    //javaで現在日時を設定
		    java.sql.Timestamp now = new java.sql.Timestamp(System.currentTimeMillis());
		    pStmtinf.setString(1, title);
		    //多分ゲームIDは見つからんので仮で作っとく
		    gaID = 1;
		    pStmtinf.setInt(2, gaID);
		    pStmtinf.setTimestamp(3, now);
		    pStmtinf.setTimestamp(4, now);
		    
		    int result1 = pStmtinf.executeUpdate();
		    int id;
		   	if (result1 == 0) {
		        throw new SQLException("挿入に失敗しました。");
		    }
		   	
		    try (ResultSet threadID = pStmtinf.getGeneratedKeys()) {
		        if (threadID.next()) {
		            id = threadID.getInt(1);
		            System.out.println("スレッドID："+id);
		        } else {
		            throw new SQLException("Insert failed, no ID obtained.");
		        }
		    }
		    //スレッド内容
		    pStmtcont.setInt(1, id);
		    pStmtcont.setString(2, cont);
		    
		    int result2 = pStmtcont.executeUpdate();
		    System.out.println("データ挿入"+result2);
		      
		   	if (result2 == 0) {
		        throw new SQLException("挿入に失敗しました。");
		    }
		   	if(result1 == 0 || result2 == 0) {
		   		return false;
		   	}
			//成功したかどうかをサーブレットに返す
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
		//仮
		return true;
	}
	
}
