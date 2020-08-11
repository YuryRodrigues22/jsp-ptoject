package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.TelefoneBean;
import connection.SingleConnetion;

public class TelefoneDAO {

	private Connection connection;

	public TelefoneDAO() {
		connection = SingleConnetion.getConnection();
	}

	public void salvar(TelefoneBean telefone) {

		try {

			String sql = " INSERT INTO telefone (numero, tipo, usuario) VALUES (?, ?, ?) ";
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setString(1, telefone.getNumero());
			insert.setString(2, telefone.getTipo());
			insert.setLong(3, telefone.getUsuario());
			insert.execute();
			connection.commit();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public List<TelefoneBean> listar(Long usuario) {

		List<TelefoneBean> list = new ArrayList<TelefoneBean>();

		try {

			String sql = " SELECT * FROM telefone WHERE usuario = " + usuario + " ORDER BY id ";
			PreparedStatement lista = connection.prepareStatement(sql);
			ResultSet resultSet = lista.executeQuery();

			while (resultSet.next()) {
				TelefoneBean telefone = new TelefoneBean();
				telefone.setId(resultSet.getLong("id"));
				telefone.setNumero(resultSet.getString("numero"));
				telefone.setTipo(resultSet.getString("tipo"));
				telefone.setUsuario(resultSet.getLong("usuario"));
				list.add(telefone);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public void delete(Long id) {

		try {

			String sql = " DELETE FROM telefone WHERE id = " + id;
			PreparedStatement deletar = connection.prepareStatement(sql);
			deletar.execute();
			connection.commit();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public void editar(TelefoneBean telefone) {

		try {

			String sql = " UPDATE telefone SET numero = ?, tipo = ? WHERE id = " + telefone.getId();
			PreparedStatement atualizar = connection.prepareStatement(sql);
			atualizar.setString(1, telefone.getNumero());
			atualizar.setString(2, telefone.getTipo());
			atualizar.executeUpdate();
			connection.commit();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public TelefoneBean consultar(Long id) throws Exception {

		TelefoneBean telefone = null;

		String sql = " SELECT * FROM telefone WHERE id = " + id;
		PreparedStatement consultar = connection.prepareStatement(sql);
		ResultSet resultSet = consultar.executeQuery();

		if (resultSet.next()) {

			telefone = new TelefoneBean();
			telefone.setId(resultSet.getLong("id"));
			telefone.setNumero(resultSet.getString("numero"));
			telefone.setTipo(resultSet.getString("tipo"));
			telefone.setUsuario(resultSet.getLong("usuario"));

		}

		return telefone;
	}

	public boolean validarNumero(String numero) throws Exception {

		String sql = " SELECT COUNT(1) AS qtd FROM telefone WHERE nome = '" + numero + "' ";
		PreparedStatement consultar = connection.prepareStatement(sql);
		ResultSet resultSet = consultar.executeQuery();

		if (resultSet.next()) {

			return resultSet.getInt("qtd") <= 0;

		}

		return false;
	}

	public boolean validarNumeroUpdate(String numero, String id) throws Exception {

		String sql = " SELECT COUNT(1) AS qtd FROM telefone WHERE nome = '" + numero + "' AND id <> '" + id + "' ";
		PreparedStatement consultar = connection.prepareStatement(sql);
		ResultSet resultSet = consultar.executeQuery();

		if (resultSet.next()) {

			return resultSet.getInt("qtd") <= 0;

		}

		return false;
	}

}
