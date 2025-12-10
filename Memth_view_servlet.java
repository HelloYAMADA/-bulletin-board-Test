

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import Beans.genre_beans;
import Beans.gtitle_beans;
import Beans.thcon_beans;
import Beans.thinf_beans;
import dao.GameGenre_Dao;
import dao.GameView_Dao;
import dao.Memth_cho_Dao;
import dao.Thcon_Dao;

/**
 * Servlet implementation class Memth_view_servlet
 */
@WebServlet("/Memth_view_servlet")
public class Memth_view_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Memth_view_servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//前の画面からスレッドタイトルを取得
		String THREAD_TITLE = request.getParameter("THREAD_TITLE");
		String flag = (String) request.getAttribute("flag");
		Memth_cho_Dao dao = new Memth_cho_Dao();
		List<thinf_beans> thinf = null;
		try {
			thinf = dao.thinf_get();
			request.setAttribute("thinf", thinf);
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		
		//Daoを起動する。thread_getメソッドを実行し、スレッド内容をBeansで受け取る。
		Thcon_Dao thcon_dao = new Thcon_Dao();
		List<thcon_beans> thcon = null;
		
		try {
			thcon = thcon_dao.thread_get(THREAD_TITLE);
			if(thcon != null) {
				System.out.println("データ取得成功");
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		
		
		
		
		GameGenre_Dao genre = new GameGenre_Dao();
		List<genre_beans> genreList = genre.game_get();
		GameView_Dao game = new GameView_Dao();
		List<gtitle_beans> gameList = game.game_tget();
		request.setAttribute("gameList", gameList);
		request.setAttribute("genreList", genreList);
		
		
		/*
		//NGワード配列を取得

		//会員NGワード配列を取得
		var mem_id = session.getAttribute("mem_id");
		if(mem_id != null) {
			MemNg_Dao memng_dao = new MemNg_Dao();
			String[] memNg = memng_dao.MemNgGet(mem_id);



		}
		*/





		//スレッド内容を次の画面へ渡す。
		request.setAttribute("thcon", thcon);
		request.setAttribute("THREAD_TITLE", THREAD_TITLE);
		request.setAttribute("flag", flag);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/memth_cho.jsp");

        dispatcher.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
