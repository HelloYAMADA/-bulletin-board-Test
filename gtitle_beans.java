package Beans;  //パッケージ名は、リーダーの階層にある上位ディレクトリに合わせてください(;;)

import java.io.Serializable;

public class gtitle_beans implements Serializable { //クラス名は、リーダーの階層にある上位ディレクトリに合わせてください(><)

    private static final long serialVersionUID = 1L;
	// フィールド変数
    private int game_id;        //ゲームID
    private String game_title;     //ゲームタイトル
    // コンストラクタ（初期値を設定）
    public gtitle_beans() {
        this.game_id = 0;
        this.game_title = "";
    }

    // コンストラクタ（引数あり）
    public gtitle_beans(int game_id, String game_title) {
        this.game_id = game_id;
        this.game_title = game_title;       
    }

    // getter と setter メソッド

    //game_id の getter/setter
    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    // game_title の getter/setter
    public String getGame_title() {
        return game_title;
    }

    public void setGame_title(String game_title) {
        this.game_title = game_title;
    }
}