package bootstrap;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import handlerFactory.HandlerFactory;

public class Bootstrap {
	public static void main(String[] args) {
		QueuedThreadPool threadPool = new QueuedThreadPool();
		threadPool.setName("server");

		// Create a Server instance.
		Server server = new Server(threadPool);

		// Create a ServerConnector to accept connections from clients.
		ServerConnector connector = new ServerConnector(server);
		connector.setPort(13579);
		

		// Add the Connector to the Server
		server.addConnector(connector);
		HandlerFactory handlerFactory = new HandlerFactory();
		handlerFactory.setResource(server);
		try {
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
