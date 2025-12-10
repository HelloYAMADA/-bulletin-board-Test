


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import Beans.Mem_Beans;
import Beans.thcon_beans;
import Beans.thinf_beans;
import dao.Memth_cho_Dao;
import dao.Thcon_Dao;
import dao.thinf_dao;

/**
 * Servlet implementation class Memth_insett_servlet
 */
@WebServlet("/Memth_insert_servlet")
public class Memth_insert_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Memth_insert_servlet() {
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
		String thread_id = request.getParameter("THREAD_ID");
		String COMMENT = request.getParameter("COMMENT");
		int THREAD_ID = Integer.parseInt(thread_id);
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
				request.setAttribute("thcon", thcon);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		String flag;
		
		if(COMMENT == null){
			System.out.println("１23");
			flag = "1";
			request.setAttribute("flag", flag);
			request.setAttribute("THREAD_TITLE", THREAD_TITLE);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/memth_cho.jsp");
			dispatcher.forward(request, response);
		}
		
		HttpSession session = request.getSession();
		Mem_Beans user = (Mem_Beans) session.getAttribute("user");
		String Stu_id = user.getMem_id();
		
		Thcon_Dao dao1 = new Thcon_Dao();
		boolean result1 = false;
		try {
			result1 = dao1.thcon_insert(THREAD_ID, Stu_id, COMMENT);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		if(result1){
			thinf_dao dao2 = new thinf_dao();
			String result2 = dao2.thinf_update(THREAD_ID);
			
			result2 = dao2.thinf_update(THREAD_ID);
			
			if(result2 != null){
				try {
					thcon = thcon_dao.thread_get(THREAD_TITLE);
					if(thcon != null) {
						System.out.println("データ取得成功");
						request.setAttribute("thcon", thcon);
					}
				}catch(SQLException e){
					e.printStackTrace();
				}
				request.setAttribute("THREAD_TITLE", THREAD_TITLE);
				System.out.println(THREAD_TITLE + "234567890");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/memth_cho.jsp");
				dispatcher.forward(request, response);
			} else {
				flag = "3";
				request.setAttribute("flag", flag);
				request.setAttribute("THREAD_TITLE", THREAD_TITLE);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/memth_cho.jsp");
				dispatcher.forward(request, response);
			}
		} else {
			flag = "2";
			request.setAttribute("flag", flag);
			request.setAttribute("THREAD_TITLE", THREAD_TITLE);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/memth_cho.jsp");
			dispatcher.forward(request, response);
		}
	}

}

