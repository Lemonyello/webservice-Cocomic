package webserviceThuvien.MySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectToDB
{
    public Connection getConnection() throws Exception {
        /*try {
            String connectionURL = "jdbc:mysql://localhost:3306/thuvien";
            Connection connection = null;
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(connectionURL, "root", "");
            return connection;
        } catch (Exception e) {
            throw e;
        }*/
    	try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		    String url = "jdbc:sqlserver://117.103.207.22:1433;databaseName=WebtoonDB";
		    Connection conn = null;
			String username = "tts2022";
			String password = "tTs20@@_v$c";
			conn = DriverManager.getConnection(url, username, password);
			return conn;
		} catch (SQLException e) {
			throw e;
		}
    }
}