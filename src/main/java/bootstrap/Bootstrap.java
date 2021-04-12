package bootstrap;

import core.PropertyUtils;
import dbFactory.DataAccess;

public class Bootstrap {
	public static void main(String[] args) {
		PropertyUtils.getInstance().init();
		DataAccess db = new DataAccess();
		db.init();
	}
}
