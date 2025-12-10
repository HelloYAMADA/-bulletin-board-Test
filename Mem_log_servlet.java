import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import Beans.Mem_Beans;
import dao.Mem_Dao;

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
		
		Mem_Beans result = null;
		result = dao.Mem_log(STU_ID, STU_PASS);
		
		if(result != null) {
			HttpSession session = request.getSession();
			session.setAttribute("user", result);
			Mem_Beans USER = (Mem_Beans) session.getAttribute("user");
			System.out.println(USER.getMem_id());
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/mem_menu.jsp");
			dispatcher.forward(request, response);
		} else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("Mem_log.html");
			dispatcher.forward(request, response);
		}
	}

}


