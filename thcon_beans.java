package Beans;  //パッケージ名は、リーダーの階層にある上位ディレクトリに合わせてください(;;)

import java.io.Serializable;

public class thcon_beans implements Serializable { //クラス名は、リーダーの階層にある上位ディレクトリに合わせてください(><)


    
	private static final long serialVersionUID = 1L;
	// フィールド変数
	private String thread_id;	   //スレッドID
    private int com_id;        // コメントID
    private String com;        // コメント内容
    private boolean dele_flag; // コメント表示するかどうかのフラグ

    // コンストラクタ（初期値を設定）
    public thcon_beans() {
    	this.thread_id = "";
        this.com_id = 0;
        this.com = "";
        this.dele_flag = false; // 初期値 FALSE
    }

    // コンストラクタ（引数あり）
    public thcon_beans(int com_id, String com, String thread_id,  boolean dele_flag) {
    	this.thread_id = thread_id;
        this.com_id = com_id;
        this.com = com;
        this.dele_flag = dele_flag;
    }

    // getter と setter メソッド
    
    // thred_id の getter/setter
    public String getThread_id() {
        return thread_id;
    }

    public void setThred_id(String thread_id) {
        this.thread_id = thread_id;
    }

    // com_id の getter/setter
    public int getCom_id() {
        return com_id;
    }

    public void setCom_id(int com_id) {
        this.com_id = com_id;
    }

    // com の getter/setter
    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    // dele_flag の getter/setter
    public boolean isDele_flag() {
        return dele_flag;
    }

    public void setDele_flag(boolean dele_flag) {
        this.dele_flag = dele_flag;
    }
}
