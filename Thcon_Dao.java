package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Beans.thcon_beans; // thcon_beansのインポート

public class Thcon_Dao {

	// データベース接続情報
	private final String JDBC_URL = "jdbc:mariadb://localhost/threadsystemdb";
	private final String DB_USER = "root";
	private final String DB_PASS = "mysql";

	public Thcon_Dao() {
		// JDBCドライバを読み込む
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			System.out.println("OK"); // データベース接続の確認
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
	}

	// スレッド名でデータ取得するメソッド
	public List<thcon_beans> thread_get(String THREAD_TITLE) throws SQLException{
		List<thcon_beans> comList = new ArrayList<>();

		// スレッド名（スレッドタイトル）からスレッドIDを取得するsql
		String thidgetsql = "SELECT THREAD_ID FROM THINF_TABLE WHERE THREAD_TITLE = ?";
		// スレッドIDからスレッド内容を取得するsql
		String thcongetsql = "SELECT * FROM THCONT_TABLE WHERE THREAD_ID = ? ORDER BY COM_ID ASC";

		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {

			int intTHREAD_ID = -1;

			// ① スレッドタイトルから THREAD_ID を取得
			try (PreparedStatement thcongetpStmt = conn.prepareStatement(thidgetsql)) {
				thcongetpStmt.setString(1, THREAD_TITLE);
				ResultSet rs = thcongetpStmt.executeQuery();

				if (rs.next()) {
					intTHREAD_ID = rs.getInt("THREAD_ID");
				} else {
					System.out.println("スレッドが見つかりません: " + THREAD_TITLE);
					return comList; // 空のリストを返す
				}
			}

			// ② THREAD_ID からコメントを取得 … COM_IDとCOMMEMTを返す
			try (PreparedStatement pStmt = conn.prepareStatement(thcongetsql)) {
				pStmt.setInt(1, intTHREAD_ID);
				ResultSet rs = pStmt.executeQuery();

				while (rs.next()) {
					int COM_ID = rs.getInt("COM_ID");
					String COMMENT = rs.getString("COMMENT");
					String strTHREAD_ID = rs.getString("THREAD_ID");
					
					// Beanに1件ずつ詰める
					thcon_beans thcon_beans = new thcon_beans(COM_ID, COMMENT, strTHREAD_ID, false);
					comList.add(thcon_beans);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return comList;
	}

	public boolean thcon_insert(int THREAD_ID, String STU_ID, String COMMENT) throws SQLException{
		// 投稿制限をチェックするSQL
		String chsql = "SELECT POS_LIM FROM THINF_TABLE WHERE THREAD_ID = ?";
		// 学籍番号の確認を行うSQL
		String stusql = "SELECT 1 FROM STUINF_TABLE WHERE STU_ID = ?"; 
		// 最新のコメントIDを取得するSQL
		String comidsql = "SELECT MAX(COM_ID) AS MAX_ID FROM THCONT_TABLE WHERE THREAD_ID = ?";
		// スレッド内容を挿入するSQL
		String thinssql = "INSERT INTO THCONT_TABLE (THREAD_ID, COM_ID, STU_ID, COMMENT) VALUES (?, ?, ?, ?)";

		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			boolean poslim = false; // 投稿制限フラグの設定

			// ①投稿制限フラグの確認
			try (PreparedStatement chStmt = conn.prepareStatement(chsql)) {
				chStmt.setInt(1, THREAD_ID);
				try (ResultSet rs = chStmt.executeQuery()) {
					if (rs.next()) {
						String limit = rs.getString("POS_LIM");
						poslim = "0".equalsIgnoreCase(limit); // POS_LIMの中身は0か1?、trueかfalse?
					} else {
						System.out.println("スレッドが存在しません。");
						return false;
					}
				}
			}

			if (!poslim) { // POS_LIMが1（投稿禁止）なら投稿失敗
				System.out.println("このスレッドは投稿が制限されています。");
				return false;
			}

			// ②学籍番号の確認
			try (PreparedStatement stStmt = conn.prepareStatement(stusql)) {
				stStmt.setString(1, STU_ID);
				try (ResultSet rs = stStmt.executeQuery()) {
					if (!rs.next()) {
						System.out.println("存在しない学籍番号です: " + STU_ID);
						return false;
					}
				}
			}

			// ③最新のCOM_IDを取得して+1
			int COM_ID = 1; // デフォルトで1
			try (PreparedStatement comidmaxStmt = conn.prepareStatement(comidsql)) {
				comidmaxStmt.setInt(1, THREAD_ID);
				try (ResultSet rs = comidmaxStmt.executeQuery()) {
					if (rs.next()) {
						COM_ID = rs.getInt("MAX_ID") + 1; // 最新のCOM_IDに+1
					}
				}
			}

			// ④スレッド内容を投稿する
			try (PreparedStatement thinspStmt = conn.prepareStatement(thinssql)) {
				thinspStmt.setInt(1, THREAD_ID);
				thinspStmt.setInt(2, COM_ID);
				thinspStmt.setString(3, STU_ID);
				thinspStmt.setString(4, COMMENT);

				int result = thinspStmt.executeUpdate();
				if (result > 0) {
					return true;
				} else {
					return false;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}