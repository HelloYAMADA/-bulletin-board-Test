package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Beans.thinf_beans;

public class Memth_cho_Dao {
	// データベース接続情報
	private final String JDBC_URL = "jdbc:mariadb://localhost/threadsystemdb";
	private final String DB_USER = "root";
	private final String DB_PASS = "mysql";

	public Memth_cho_Dao() {
		// JDBCドライバを読み込む
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
	}

	// 人気のスレッドを取得するメソッド
	public List<thinf_beans> thinf_get() throws SQLException {
		List<thinf_beans> thinfList = new ArrayList<>();

		// スレッド名（スレッドタイトル）からスレッド情報を取得するsql
		String thinfgetsql = "SELECT * FROM THINF_TABLE ORDER BY  UPD_DATE DESC LIMIT 3"; // 最終更新日でソート
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			try (PreparedStatement thinfgetpStmt = conn.prepareStatement(thinfgetsql)) {
				ResultSet rs = thinfgetpStmt.executeQuery();
				
				while (rs.next()) {
					int THREAD_ID = rs.getInt("THREAD_ID");
					String THREAD_TITLE = rs.getString("THREAD_TITLE");
					thinf_beans thinf = new thinf_beans(THREAD_ID, THREAD_TITLE);
					thinfList.add(thinf);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return thinfList;
		}
	}
}
