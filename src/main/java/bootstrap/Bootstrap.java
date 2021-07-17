package bootstrap;

import core.PropertyUtils;
import httpAdapter.HttpServerFactory;

public class Bootstrap {
	public static void main(String[] args) {
		PropertyUtils.getInstance().init();
		HttpServerFactory server = new HttpServerFactory();
		server.init();
	}
}
