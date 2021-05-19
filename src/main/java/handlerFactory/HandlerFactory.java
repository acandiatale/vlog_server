package handlerFactory;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.EnumSet;

import org.eclipse.jetty.rewrite.handler.RewriteHandler;
import org.eclipse.jetty.rewrite.handler.RewriteRegexRule;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ErrorPageErrorHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.eclipse.jetty.util.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import filters.CorsSigninCheckFilter;
import jakarta.servlet.DispatcherType;
import servletManager.ErrorPageServlet;
import servletManager.LoginServlet;
import servletManager.PostServlet;

public class HandlerFactory {
	private File resourceFile;
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	public HandlerFactory() {
		this(System.getProperty("ResourcePath", "dist/"));
	}
	
	public HandlerFactory(String resourcePath) {
		resourceFile = new File("dist/");
	}
	
	public void setHandlers(Server server) {
		URI uri = URI.create(resourceFile.toURI().toASCIIString().replace("/index.html$", "/"));
		
		if(uri == null) {
			System.out.println("URIResource loading fail!! dist directory not founded");
			logger.info("URIResource loading fail!! dist directory not founded");
			System.exit(1);
		}
		
		logger.info("resource URI : " + uri.toString());
		System.out.println("resource URI : " + uri.toString());
		
		HandlerList handlerList = new HandlerList();
		
//		ServletContextHandler rootContext = new ServletContextHandler(ServletContextHandler.SESSIONS);
//		rootContext.add
		
		RewriteHandler rewrite = new RewriteHandler();
		rewrite.setRewriteRequestURI(true);		
		rewrite.setRewritePathInfo(false);
		rewrite.setOriginalPathAttribute("requestedPath");
		
//		rewrite.addRule(new CompactPathRule());
//		rewrite.addRule(new RewriteRegexRule("(\\/introduce)?(\\/vlog)?(\\/post)?(\\/error)?", "/index.html"));
		
		rewrite.addRule(new RewriteRegexRule("\\/?\\w+", "/index.html"));
		
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		
		try {
			context.setContextPath("/");
			context.setWelcomeFiles(new String[] {"index.html"});
			context.setBaseResource(Resource.newResource(uri));
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		context.addServlet(PostServlet.class, "/post/test");
		context.addServlet(ErrorPageServlet.class, "/error");
		context.addServlet(LoginServlet.class, "/jwt");
		
		FilterHolder filterHolder = context.addFilter(CorsSigninCheckFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));
		filterHolder.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
		filterHolder.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
		filterHolder.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET");
		filterHolder.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "Content-Type");
		
		filterHolder.setAsyncSupported(true);
		
		ErrorPageErrorHandler errorMapper = new ErrorPageErrorHandler();
		errorMapper.addErrorPage(404, "/error");
		context.setErrorHandler(errorMapper);
		
		rewrite.setHandler(context);
		
		ServletHolder defHolder = new ServletHolder("default", DefaultServlet.class);
//		defHolder.setInitParameter("dirAllowed", "false");
		defHolder.setAsyncSupported(true);
//		defHolder.setInitParameter("resourceBase", "dist/");
		context.addServlet(defHolder, "/");
		
		DefaultHandler def = new DefaultHandler();
		handlerList.setHandlers(new Handler[] {rewrite, def});
		server.setHandler(handlerList);
	}
	
	
}
