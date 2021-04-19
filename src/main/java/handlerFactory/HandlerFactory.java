package handlerFactory;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;

import org.eclipse.jetty.rewrite.RewriteCustomizer;
import org.eclipse.jetty.rewrite.handler.CompactPathRule;
import org.eclipse.jetty.rewrite.handler.RedirectRegexRule;
import org.eclipse.jetty.rewrite.handler.RewriteHandler;
import org.eclipse.jetty.rewrite.handler.RewriteRegexRule;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ErrorPageErrorHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import servletManager.ErrorPageServlet;
import servletManager.TestServlet;

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
		
		RewriteHandler rewrite = new RewriteHandler();
		rewrite.setRewriteRequestURI(true);		
		rewrite.setRewritePathInfo(false);
		rewrite.setOriginalPathAttribute("requestedPath");
		
//		rewrite.addRule(new CompactPathRule());
		rewrite.addRule(new RewriteRegexRule("(\\/introduce)?", "/index.html"));
		
//		rewrite.addRule(new RewriteRegexRule("(\\/.*)", "/index.html"));
		
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		
		try {
			context.setContextPath("/");
			context.setWelcomeFiles(new String[] {"index.html"});
			context.setBaseResource(Resource.newResource(uri));
			context.addServlet(ErrorPageServlet.class, "/errorpage");
			ErrorPageErrorHandler errorMapper = new ErrorPageErrorHandler();
			errorMapper.addErrorPage(404, "/errorpage");
			context.setErrorHandler(errorMapper);
			rewrite.setHandler(context);
			
			ServletHolder defHolder = new ServletHolder("default", DefaultServlet.class);
			defHolder.setInitParameter("dirAllowed", "false");
			defHolder.setAsyncSupported(true);
//			defHolder.setInitParameter("resourceBase", "dist/");
			context.addServlet(defHolder, "/");
			
			DefaultHandler def = new DefaultHandler();
			handlerList.setHandlers(new Handler[] {rewrite, def});
			server.setHandler(handlerList);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	
}
