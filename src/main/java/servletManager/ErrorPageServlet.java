package servletManager;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ErrorPageServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8134499209876045190L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setStatus(404);
//		resp.sendRedirect("/error");
		resp.sendError(404, "not found");
	}
}
