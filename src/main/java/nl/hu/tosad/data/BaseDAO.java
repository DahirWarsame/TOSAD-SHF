package nl.hu.tosad.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseDAO {
    private String connectionURL = "jdbc:oracle:thin:@ondora02.hu.nl:8521/cursus02.hu.nl";
    private String username = "tosad_2017_2c_team2";
	private String password = "tosad_2017_2c_team2";
    private String driver = "oracle.jdbc.driver.OracleDriver";
    private Connection con;

	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
    BaseDAO() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    Connection getConnection() {
        // URL, User and Password
        try {
            con = DriverManager.getConnection(connectionURL, username, password);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return con;
    }

    void close() throws SQLException {
        if (con != null) {
            con.close();
        }
    }
}

