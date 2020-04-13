package com.example.dbretrieval;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "api" path)
 */
@Path("api")
public class MyResource {

	/**
	 * Method handling HTTP GET requests. The returned object will be sent to the
	 * client as APPLICATION_JSON media type.
	 *
	 * @return String that will be returned as a APPLICATION_JSON response.
	 */
	@GET
	@Path("checkpalindrome")
	@Produces(MediaType.APPLICATION_JSON)
	public String checkPalindrome(@QueryParam("word") String word) {
		String result = null;
		try {
			result = checkForPalindrome(word);
		} catch (SQLException e) {
			System.out.println("Error encountered: " + e);
			e.printStackTrace();
		}
		System.out.println(result);
		return result;
	}

	/**
	 * Method handling call of database Function. The word passed into the URL is
	 * used as input to the database Function, with the output from the call
	 * returned.
	 *
	 * @return Call result.
	 */
	public static String checkForPalindrome(String word) throws SQLException {
		String sql = "{? = call CHECKFORPALINDROME(?)}";
		try (Connection conn = getConnection(); java.sql.CallableStatement stmt = conn.prepareCall(sql);) {
			stmt.setString(2, word);
			stmt.registerOutParameter(1, java.sql.Types.VARCHAR);
			stmt.execute();
			String stmtResult = stmt.getString(1);
			return stmtResult;
		}
	}

	/**
	 * Method handling establishment of a database connection.
	 *
	 * @return Connection object
	 */
	public static Connection getConnection() {
		Properties prop = ReadPropertyFile();
		Connection conn = null;
		try {
			// need to load class explicitly for Tomcat
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(prop.getProperty("db.URL"), prop.getProperty("db.user"),
					prop.getProperty("db.password"));

		} catch (SQLException e) {
			System.out.println("Error encountered: " + e);
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Error encountered: " + e);
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * Method handling reading of configuration file containing database connection
	 * details.
	 *
	 * @return Properties object read from file.
	 */
	private static Properties ReadPropertyFile() {
		Properties prop = new Properties();
		try (InputStream input = new FileInputStream("c:\\config.properties")) {
			prop.load(input);
		} catch (FileNotFoundException e) {
			System.out.println("Error encountered: " + e);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error encountered: " + e);
			e.printStackTrace();
		}
		return prop;
	}

}
