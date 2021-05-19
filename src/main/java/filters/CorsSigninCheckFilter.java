package filters;

import java.io.IOException;

import org.eclipse.jetty.servlets.CrossOriginFilter;

import core.JWTCipher;
import core.JWTManager;
import core.VerifyType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CorsSigninCheckFilter extends CrossOriginFilter {

	@Override
	public void init(FilterConfig config) throws ServletException {
		super.init(config);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		Cookie[] cookies = httpRequest.getCookies();
		
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				if(cookie.getName() == "token") {
					JWTCipher cipher = JWTManager.getJWTCipher();
					String prevToken = cookie.getValue();
					int status = cipher.verifyToken(prevToken);
					
					switch(status) {
						case 0 :
							break;
						case 1 :
							prevToken = "expired";
							break;
						case 2 : 
							prevToken = cipher.generateToken();
							break;
						case 3 :
							prevToken = "Failed";
							break;
					}
					cookie.setValue(prevToken);
					httpResponse.addCookie(cookie);
					break;
				}
			}
		}
		super.doFilter(httpRequest, httpResponse, chain);
	}

	@Override
	protected boolean isEnabled(HttpServletRequest request) {
		return super.isEnabled(request);
	}

	@Override
	public void destroy() {
		super.destroy();
	}

	
}
