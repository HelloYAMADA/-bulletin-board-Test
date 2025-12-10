package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * スレッド情報テーブルに関するDB操作を行うDAOクラス
 */
public class thinf_dao {

    private final String JDBC_URL = "jdbc:mariadb://localhost/threadsystemdb";
    private final String DB_USER = "root";
    private final String DB_PASS = "mysql";

    // コンストラクタ
    public thinf_dao() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            System.out.println("thinf_dao : JDBCドライバOK");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("JDBCドライバを読み込めませんでした", e);
        }
    }

    /**
     * スレッド情報を更新し、更新後のコメント数と最終更新日を取得する
     * 
     * @param threadId スレッドID
     * @return 更新情報の文字列（存在しない場合はnull）
     */
    public String thinf_update(int threadId) {
        String updateSql = 
            "UPDATE THINF_TABLE " +
            "SET COM_COUNT = (SELECT COUNT(*) FROM THCONT_TABLE WHERE THREAD_ID = ?), " +
            "    UPD_DATE = CURRENT_TIMESTAMP " +
            "WHERE THREAD_ID = ?";

        String selectSql = "SELECT COM_COUNT, UPD_DATE FROM THINF_TABLE WHERE THREAD_ID = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {

            // ① 更新処理
            try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
                pstmt.setInt(1, threadId);
                pstmt.setInt(2, threadId);
                int updatedRows = pstmt.executeUpdate();
                if (updatedRows == 0) {
                    return null; // スレッドIDが存在しない場合
                }
            }

            // ② 更新後の情報を取得
            try (PreparedStatement pstmt2 = conn.prepareStatement(selectSql)) {
                pstmt2.setInt(1, threadId);
                try (ResultSet rs = pstmt2.executeQuery()) {
                    if (rs.next()) {
                        int comCount = rs.getInt("COM_COUNT");
                        String updDate = rs.getString("UPD_DATE");
                        return "更新後のコメント数: " + comCount + ", 最終更新日: " + updDate;
                    } else {
                        return null;
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
