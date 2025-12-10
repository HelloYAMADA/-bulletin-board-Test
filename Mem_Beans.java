package Beans;  //パッケージ名は、リーダーの階層にある上位ディレクトリに合わせてください(;;)

import java.io.Serializable;

public class Mem_Beans implements Serializable { //クラス名は、リーダーの階層にある上位ディレクトリに合わせてください(><)


    private static final long serialVersionUID = 1L;
	// フィールド変数
    private String mem_id;        // 学籍番号
    private String user_name;        // ユーザー名

    // コンストラクタ（初期値を設定）
    public Mem_Beans() {
        this.mem_id = "";
        this.user_name = "";
    }

    // コンストラクタ（引数あり）
    public Mem_Beans(String mem_id, String user_name) {
        this.mem_id = mem_id;
        this.user_name = user_name;
    }

    // getter と setter メソッド

    // mem_id の getter/setter
    public String getMem_id() {
        return mem_id;
    }

    public void setMem_id(String mem_id) {
        this.mem_id = mem_id;
    }

    // user_name の getter/setter
    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }


}
