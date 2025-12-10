package Beans;  //パッケージ名は、リーダーの階層にある上位ディレクトリに合わせてください(;;)

import java.io.Serializable;

public class thsear_beans implements Serializable { //クラス名は、リーダーの階層にある上位ディレクトリに合わせてください(><)

    // フィールド変数
    private String th_name;        //スレッド名
    private int th_id;        // スレッドID

    // コンストラクタ（初期値を設定）
    public thsear_beans() {
        this.th_name = "";
        this.th_id = 0;
    }

    // コンストラクタ（引数あり）
    public thsear_beans(String th_name, int th_id) {
        this.th_name = th_name;
        this.th_id = th_id;
    }

    // getter と setter メソッド

    //th_name の getter/setter
    public String getTh_name() {
        return th_name;
    }

    public void setTh_name(String th_name) {
        this.th_name = th_name;
    }

    // th_id の getter/setter
    public int getTh_id() {
        return th_id;
    }

    public void setTh_id(int th_id) {
        this.th_id = th_id;
    }

}