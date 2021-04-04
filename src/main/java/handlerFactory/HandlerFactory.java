package handlerFactory;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.resource.Resource;

import servletManager.TestServlet;

public class HandlerFactory {
	private File resourceFile = new File("dist/");
	
	public void setResource(Server server) {
		URI uri = URI.create(resourceFile.toURI().toASCIIString().replace("/index.html$", "/"));
		if(uri == null) {
			System.out.println("uri resource loading fail!! resource file not found");
			System.exit(1);
		}
		System.out.println("resource URI : " + uri.toString());
		
//		HandlerList handlerList = new HandlerList();
		
//		RewriteHandler rewrite = new RewriteHandler();
//		rewrite.setRewriteRequestURI(true);
//		rewrite.setRewritePathInfo(false);
//		rewrite.setOriginalPathAttribute("requestedPath");
//
//		RewriteRegexRule rule = new RewriteRegexRule();
//		rewrite.addRule(rule);
		
		ResourceHandler handler = new ResourceHandler();
		try {
			handler.setBaseResource(Resource.newResource(uri));
			handler.setDirectoriesListed(false);
			handler.setWelcomeFiles(new String[] {"index.html"});
			handler.setAcceptRanges(true);
			server.setHandler(handler);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	
}
