package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Beans.thsear_beans;

public class Thsearch_Dao {
	// データベース接続情報
	private final String JDBC_URL = "jdbc:mariadb://localhost/threadsystemdb";
	private final String DB_USER = "root";
	private final String DB_PASS = "mysql";

	public Thsearch_Dao() {
		// JDBCドライバを読み込む
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
	}

	// ゲームタイトル,ジャンルIDを組み合わせてスレッドを検索するメソッド
	public List<thsear_beans> memth_list(String GAME_TITLE,int GENRE_ID)
			throws SQLException {
		List<thsear_beans> memthList = new ArrayList<>();

		// ゲームタイトルまたはジャンルIDからスレッドタイトル、スレッドIDを検索するSQL(WHERE 1=1 で条件を後付けできる）
		StringBuilder thsearsql = new StringBuilder();
		// SQLの前半部分
		thsearsql.append("SELECT THINF_TABLE.THREAD_ID, THINF_TABLE.THREAD_TITLE FROM THINF_TABLE LEFT OUTER JOIN GAMEINF_TABLE ON THINF_TABLE.GAME_ID = GAMEINF_TABLE.GAME_ID WHERE 1 = 1 ");

		// 検索条件をリストにまとめる
		List<String> searcon = new ArrayList<>();
		
		// 選択された検索条件を追加（未選択は追加されない）
		// GAME_TITLEを検索条件に追加
		if (GAME_TITLE != null && !GAME_TITLE.isEmpty()) {
			searcon.add("GAMEINF_TABLE.GAME_TITLE LIKE ?"); // ゲームタイトルのあいまい検索
		}

		/* 今後追加予定
		// THREAD_TITLEを検索条件に追加
		if (THREAD_TITLE != null && !THREAD_TITLE.isEmpty()) {
			searcon.add("THINF_TABLE.THREAD_TITLE LIKE ?");
		}
		*/

		// GANRE_IDを検索条件に追加
		if (GENRE_ID != 0) {
			// GENRE_ID1 用
			StringBuilder GENRE_ID1 = new StringBuilder("GAMEINF_TABLE.GENRE_ID1 = ?");
			searcon.add(GENRE_ID1.toString());

			// GENRE_ID2 用
			StringBuilder GENRE_ID2 = new StringBuilder("GAMEINF_TABLE.GENRE_ID2 = ?");
			searcon.add(GENRE_ID2.toString());
		}

		// thsearsqlにserconを追加
		if (!searcon.isEmpty()) {
			thsearsql.append("AND (");
			thsearsql.append(String.join(" OR ", searcon));
			thsearsql.append(") ");
		}

		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
				PreparedStatement thsearStmt = conn.prepareStatement(thsearsql.toString())) {

			int index = 1;
			
			// 指定された検索条件のみをセット
			// GAME_TITLEをセット
			if (GAME_TITLE != null && !GAME_TITLE.isEmpty()) {
				thsearStmt.setString(index++, "%" + GAME_TITLE + "%");
			}

			/* 今後追加予定
			// THREAD_TITLEをセット
			if (THREAD_TITLE != null && !THREAD_TITLE.isEmpty()) {
				thsearStmt.setString(index++, "%" + THREAD_TITLE + "%");
			}
			*/

			// GENRE_IDをセット
			if (GENRE_ID != 0) {
				// GENRE_ID1をセット
				thsearStmt.setInt(index++, GENRE_ID);
				// GENRE_ID2をセット
				thsearStmt.setInt(index++, GENRE_ID);
			}

			ResultSet rs = thsearStmt.executeQuery();
			while (rs.next()) {
				memthList.add(new thsear_beans(
						rs.getString("THREAD_TITLE"),
						rs.getInt("THREAD_ID")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return memthList;
	}
}