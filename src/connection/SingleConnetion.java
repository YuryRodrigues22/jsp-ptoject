package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnetion {
	
	private static String user = "postgres";
	private static String password = "admin";
	private static String banco = "jdbc:postgresql://localhost:5432/DBCont?autoReconnect=true";
	private static Connection connection = null;
	
	static {
		conectar();
	}
	
	public SingleConnetion() {
		conectar();
	}

	private static void conectar() {
		try {
			
			if(connection == null) {
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(banco, user, password);
				connection.setAutoCommit(false);
			}
			
		} catch (Exception e) {
			throw new RuntimeException("Erro ao conectar ao Banco");
		}
	}
	
	public static Connection getConnection() {
		return connection;
	}
}
