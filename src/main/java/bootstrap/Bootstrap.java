package bootstrap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Bootstrap {
	public static void main(String[] args) {
		HikariConfig config = new HikariConfig("repository/DBConnection.properties");
		HikariDataSource ds = new HikariDataSource(config);
		try {
			Connection conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM admin");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String fin = rs.getString("id");
				System.out.println(fin);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
