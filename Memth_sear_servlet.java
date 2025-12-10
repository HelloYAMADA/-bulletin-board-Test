

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
import Beans.thsear_beans;
import dao.GameGenre_Dao;
import dao.GameView_Dao;
import dao.Memth_cho_Dao;
import dao.Thcon_Dao;
import dao.Thsearch_Dao;

/**
 * Servlet implementation class Memth_sear_servlet
 */
@WebServlet("/Memth_sear_servlet")
public class Memth_sear_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Memth_sear_servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		String GAME_TITLE = request.getParameter("GAME_TITLE");
		String THREAD_TITLE = request.getParameter("THREAD_TITLE");
		String genre_id = request.getParameter("GENRE_ID");
		int GENRE_ID = 0;
		if(genre_id != null) {
			GENRE_ID = Integer.parseInt(genre_id);
		}
			
		
		Thsearch_Dao dao = new Thsearch_Dao();
		List<thsear_beans> result = null;
		try {
			result = dao.memth_list(GAME_TITLE,GENRE_ID);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		Memth_cho_Dao d = new Memth_cho_Dao();
		List<thinf_beans> thinf = null;
		try {
			thinf = d.thinf_get();
			request.setAttribute("thinf", thinf);
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		 
		for(thinf_beans b : thinf) {
			System.out.println(b.getThred_title());
		}
		
		
		
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
		request.setAttribute("thcon", thcon);
		
		
		if(result != null) {
			request.setAttribute("Search", result);			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/memth_cho.jsp");
			dispatcher.forward(request, response);
		} else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/memth_cho.jsp");
			dispatcher.forward(request, response);
		}
		
	}

}
