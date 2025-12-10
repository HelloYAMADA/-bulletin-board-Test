

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

/**
 * Servlet implementation class Memth_cho_servlet
 */
@WebServlet("/Memth_cho_servlet")
public class Memth_cho_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Memth_cho_servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<thcon_beans> THCON = (List<thcon_beans>) request.getAttribute("thcon");
		String THREAD_TITLE = (String) request.getAttribute("THREAD_TITLE");
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
		 
		for(thinf_beans b : thinf) {
			System.out.println(b.getThred_title());
		}
		
		GameGenre_Dao genre = new GameGenre_Dao();
		List<genre_beans> genreList = genre.game_get();
		GameView_Dao game = new GameView_Dao();
		List<gtitle_beans> gameList = game.game_tget();
		request.setAttribute("gameList", gameList);
		request.setAttribute("genreList", genreList);
		
		request.setAttribute("thcon", THCON);
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
		/**request.setCharacterEncoding("UTF-8");
		String THREAD_ID = request.getParameter("THREAD_ID");
		String THREAD_TITLE = request.getParameter("THREAD_TITLE");
		String STU_ID = request.getParameter("STU_ID");
		String GAME_ID = request.getParameter("GAME_ID");
		String CRE_DATE = request.getParameter("CRE_DATE");*/
	}

}
