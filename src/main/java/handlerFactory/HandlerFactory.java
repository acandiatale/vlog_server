package handlerFactory;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;

import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.resource.Resource;

import servletManager.TestServlet;

public class HandlerFactory {
	private static File resourceFile = new File("/dist");
	
	public void setResource() {
		URI uri = URI.create(resourceFile.toURI().toASCIIString().replace("/index.html$", "/"));
		if(uri == null) {
			System.out.println("uri resource loading fail!! resource file not found");
			System.exit(1);
		}
		System.out.println("resource URI : " + uri.toString());
		
		HandlerList handlerList = new HandlerList();
		
//		RewriteHandler rewrite = new RewriteHandler();
//		rewrite.setRewriteRequestURI(true);
//		rewrite.setRewritePathInfo(false);
//		rewrite.setOriginalPathAttribute("requestedPath");
//
//		RewriteRegexRule rule = new RewriteRegexRule();
//		rewrite.addRule(rule);
		
		ContextHandlerCollection contexts = new ContextHandlerCollection();
		handlerList.addHandler(contexts);
		
		ServletContextHandler servletHandler = new ServletContextHandler();
		servletHandler.setContextPath("/");
		servletHandler.setWelcomeFiles(new String[] {"index.html"});
		try {
			servletHandler.setBaseResource(Resource.newResource(uri));
		} catch (MalformedURLException e) {
			System.out.println("faile to set resourceBase");
			e.printStackTrace();
		}
		servletHandler.addServlet(TestServlet.class, "/test");
		
	}
}
