


import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import Beans.genre_beans;
import Beans.gtitle_beans;
import dao.GameGenre_Dao;
import dao.GameView_Dao;

/**
 * Servlet implementation class Mem_menu_servlet
 */
@WebServlet("/Mem_menu_servlet")
public class Mem_menu_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Mem_menu_servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		GameGenre_Dao genre = new GameGenre_Dao();
		List<genre_beans> genreList = genre.game_get();
		GameView_Dao game = new GameView_Dao();
		List<gtitle_beans> gameList = game.game_tget();
		
		
		System.out.println(genreList.get(0).getGenre_id() + "::" + gameList.get(0).getGame_id());
		
		
		if (gameList != null && genreList != null) {
			request.setAttribute("gameList", gameList);
			request.setAttribute("genreList", genreList);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/memth_cre.jsp");
			dispatcher.forward(request, response);
		} else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/mem_menu.jsp");
			dispatcher.forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

//DAO Beans