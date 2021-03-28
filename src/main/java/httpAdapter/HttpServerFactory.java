package httpAdapter;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

public class HttpServerFactory {

	public void init() {
		QueuedThreadPool threadPool = new QueuedThreadPool(15);
		threadPool.setName("HttpServer");
		
		Server httpServer = new Server(threadPool);
		
		Connector connector = new ServerConnector(httpServer);
	}
}
