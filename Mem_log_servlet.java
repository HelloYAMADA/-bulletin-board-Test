

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class Mem_log_servlet
 */
@WebServlet("/Mem_log_servlet")
public class Mem_log_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Mem_log_servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		String STU_ID = request.getParameter("STU_ID");
		String STU_PASS = request.getParameter("STU_PASS");
		
		//Mem_beans mem = new Mem_beans();
		
		Mem_Dao dao  = new Mem_Dao();
		
		Mem_beans result = false;
		try {
			result = dao.Mem_log(STU_ID, STU_PASS);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		if(result) {
			HttpSession session = request.getSession();
			session.setAttribute("user", result);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB_INF/jsp/Mem_menu.jsp");
			dispatcher.forward(request, response);
		} else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB_INF/jsp/Mem_log.jsp");
			dispatcher.forward(request, response);
		}
	}

}


