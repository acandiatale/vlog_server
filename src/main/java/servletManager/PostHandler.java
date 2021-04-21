package servletManager;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PostHandler extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6670490092333005763L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Request parm : id = " + req.getParameter("id"));
		resp.setStatus(200);
		resp.setContentType("application/json; charset=UTF-8");
		Gson gson = new Gson();
		JsonObject obj = new JsonObject();
		obj.addProperty("name", "jawsubak");
		resp.getWriter().write(gson.toJson(obj));
		
	}

	

}
