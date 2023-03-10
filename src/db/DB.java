package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {
	private static Properties loadDbProperties() {
		try (FileInputStream dbProperties = new FileInputStream("db.properties")) {
			Properties properties = new Properties();
			properties.load(dbProperties);
			return properties;
		} catch (IOException e) {
			throw new DbException(e.getMessage());
		}
	}

	private static Connection connection = null;

	public static Connection startConnection() {
		if (connection == null) {
			try {
				Properties dbProperties = loadDbProperties();
				String dbUrl = dbProperties.getProperty("dburl");
				connection = DriverManager.getConnection(dbUrl, dbProperties);
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
		return connection;
	}

	public static void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}

	public static void closeStatement(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}

	public static void closeResultSet(ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
}