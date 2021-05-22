package servletManager;

import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import core.JWTManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4358266875337925453L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Cookie[] cookies = req.getCookies();
//		System.out.println(req.getContentType());
		String token = JWTManager.getJWTCipher().generateToken();
		System.out.println("token generate : " + token);
		resp.setStatus(200);
		resp.setContentType("application/json; charset=UTF-8");
		Cookie cookie = new Cookie("token", token);
		resp.addCookie(cookie);
		Gson gson = new Gson();
		JsonObject obj = new JsonObject();
		obj.addProperty("token", token);
		PrintWriter pw = resp.getWriter();
		pw.write(gson.toJson(obj));
		pw.close();
		JWTManager.remove();
	}
	
}
