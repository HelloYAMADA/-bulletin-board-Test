package Beans;  //パッケージ名は、リーダーの階層にある上位ディレクトリに合わせてください(;;)

import java.io.Serializable;

public class genre_beans implements Serializable { private static final long serialVersionUID = 1L;
//クラス名は、リーダーの階層にある上位ディレクトリに合わせてください(><)

    // フィールド変数
    private int genre_id;       //ジャンルID
    private String genre_title;	   //ジャンルタイトル

    // コンストラクタ（初期値を設定）
    public genre_beans() {
        this.genre_id = 0;
        this.genre_title = "";
    }

    // コンストラクタ（引数あり）
    public genre_beans(int genre_id, String genre_title) {
        this.genre_id = genre_id;
        this.genre_title = genre_title;		
        
    }

    // getter と setter メソッド
 // genre_id の getter/setter
    public int getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(int genre_id) {
        this.genre_id = genre_id;
    }
    
 // genre_title の getter/setter
    public String getGenre_title() {
        return genre_title;
    }

    public void setGenre_title(String genre_title) {
        this.genre_title = genre_title;
    }
}