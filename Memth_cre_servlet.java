

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.memth_cre;

/**
 * Servlet implementation class Memth_cre_Servlet
 */
@WebServlet("/Memth_cre_servlet")
public class Memth_cre_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Memth_cre_servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		String THREAD_TITLE = request.getParameter("THREAD_TITLE");
		String GAME_TITLE = request.getParameter("GAME_TITLE");
		String GENRE_NAME = request.getParameter("GENRE_NAME");
		String COMMENT = request.getParameter("COMMENT");

		System.out.println(THREAD_TITLE);
		System.out.println(GAME_TITLE);
		System.out.println(GENRE_NAME);
		System.out.println(COMMENT);
		/*Thsre_beans thsre_beans = new Thsre_beans();
		Thsre_beans.setThreadTitle(THREAD_TITLE);
		Thsre_beans.setComment(COMMENT);

		Gtitle_beans gtitle_beans = new Gtitle_beans();
		Gtitle_beans.setGameTitle(GAME_TITLE); //タイトルからゲームIDを持って来ないといけない*/

		memth_cre dao = new memth_cre();
		boolean result = false;
		try {
			result = dao.memth_method(THREAD_TITLE, GAME_TITLE, GENRE_NAME, COMMENT);
			if(result) {
				System.out.println("データ挿入成功");
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		//boolean result = dao.memth_method(THREAD_TITLE, GAME_TITLE, GENRE_NAME, COMMENT);

		if(result) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/mem_menu.jsp");
			dispatcher.forward(request, response);
		} else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/memth_cre.jsp");
			dispatcher.forward(request, response);
		}
	}

}
