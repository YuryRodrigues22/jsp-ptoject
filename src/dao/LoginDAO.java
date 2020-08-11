package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.SingleConnetion;

public class LoginDAO {

	private Connection connection;
	
	public LoginDAO() {
		connection = SingleConnetion.getConnection();
	}
	
	public boolean validarLogin(String login, String senha) throws Exception {
		
		String sql = " SELECT * FROM usuario WHERE login = '" + login + "' AND senha = '" + senha + "' ";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		if(resultSet.next()) {
			return true;
		}else {
			return false;
		}

	}
}
