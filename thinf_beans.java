package Beans;  //パッケージ名は、リーダーの階層にある上位ディレクトリに合わせてください(;;)

import java.io.Serializable;

public class thinf_beans implements Serializable { //クラス名は、リーダーの階層にある上位ディレクトリに合わせてください(><)


    // フィールド変数
    private int thred_id;        // スレッドID
    private String thred_title;		// スレッドタイトル

    // コンストラクタ（初期値を設定）
    public thinf_beans() {
        this.thred_id = 0;
        this.thred_title = "";
    }

    // コンストラクタ（引数あり）
    public thinf_beans(int thred_id, String thred_title) {
        this.thred_id = thred_id;
        this.thred_title = thred_title;
    }

    // getter と setter メソッド

    // thred_id の getter/setter
    public int getThred_id() {
        return thred_id;
    }

    public void setThred_id(int thred_id) {
        this.thred_id = thred_id;
    }

    // thred_title の getter/setter
    public String getThred_title() {
        return thred_title;
    }

    public void setThred_title(String thred_title) {
        this.thred_title = thred_title;
    }
}
