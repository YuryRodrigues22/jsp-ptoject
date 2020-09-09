package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.UsuarioBean;
import connection.SingleConnetion;

public class UsuarioDAO {

	private Connection connection;

	public UsuarioDAO() {
		connection = SingleConnetion.getConnection();
	}

	public void salvar(UsuarioBean usuario) {
		try {
			String sql = " INSERT INTO usuario(nome, login, senha, cep, rua, bairro, cidade, estado, ibge, fotobase64, contenttype, curriculobase64, contenttypecurriculo, fotobase64miniatura) "
					+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setString(1, usuario.getNome());
			insert.setString(2, usuario.getLogin());
			insert.setString(3, usuario.getSenha());
			insert.setString(4, usuario.getCep());
			insert.setString(5, usuario.getRua());
			insert.setString(6, usuario.getBairro());
			insert.setString(7, usuario.getCidade());
			insert.setString(8, usuario.getUf());
			insert.setString(9, usuario.getIbge());
			insert.setString(10, usuario.getFotoBase64());
			insert.setString(11, usuario.getContentType());
			insert.setString(12, usuario.getCurriculoBase64());
			insert.setString(13, usuario.getContentTypeCurriculo());
			insert.setString(14, usuario.getFotoBase64Miniatura());

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

	public List<UsuarioBean> listar() {

		List<UsuarioBean> list = new ArrayList<UsuarioBean>();

		try {

			String sql = " SELECT * FROM usuario WHERE id <> 29 ORDER BY id ";
			PreparedStatement lista = connection.prepareStatement(sql);
			ResultSet resultSet = lista.executeQuery();

			while (resultSet.next()) {
				UsuarioBean usuarioBean = new UsuarioBean();
				usuarioBean.setId(resultSet.getLong("id"));
				usuarioBean.setNome(resultSet.getString("nome"));
				usuarioBean.setLogin(resultSet.getString("login"));
				usuarioBean.setSenha(resultSet.getString("senha"));
				usuarioBean.setCep(resultSet.getString("cep"));
				usuarioBean.setRua(resultSet.getString("rua"));
				usuarioBean.setBairro(resultSet.getString("bairro"));
				usuarioBean.setCidade(resultSet.getString("cidade"));
				usuarioBean.setUf(resultSet.getString("estado"));
				usuarioBean.setIbge(resultSet.getString("ibge"));
//				usuarioBean.setFotoBase64(resultSet.getString("fotobase64"));
				usuarioBean.setFotoBase64Miniatura(resultSet.getString("fotobase64miniatura"));
				usuarioBean.setContentType(resultSet.getString("contenttype"));
				usuarioBean.setCurriculoBase64(resultSet.getString("curriculobase64"));
				usuarioBean.setContentTypeCurriculo(resultSet.getString("contenttypecurriculo"));

				list.add(usuarioBean);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;

	}

	public void delete(Long id) {

		try {

			String sql = " DELETE FROM usuario WHERE id = " + id + " AND id <> 29 ";
			PreparedStatement delete = connection.prepareStatement(sql);
			delete.execute();

			connection.commit();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void atualizar(UsuarioBean usuario) {

		try {

			StringBuilder sql = new StringBuilder();

			sql.append(" UPDATE usuario SET nome = ?, login = ?, senha = ?, ");
			sql.append("cep = ?, rua = ?, bairro = ?, ");
			sql.append("cidade = ?, estado = ?");

			if (usuario.isAtualizarImage()) {
				sql.append(", fotobase64 = ?, contenttype = ?");
			}

			if (usuario.isAtualizarPdf()) {
				sql.append(", curriculobase64 = ?, contenttypecurriculo = ?");
			}

			if (usuario.isAtualizarImage()) {
				sql.append(", fotobase64miniatura = ? ");
			}

			sql.append(" WHERE id = " + usuario.getId());

			PreparedStatement atualizar = connection.prepareStatement(sql.toString());
			atualizar.setString(1, usuario.getNome());
			atualizar.setString(2, usuario.getLogin());
			atualizar.setString(3, usuario.getSenha());
			atualizar.setString(4, usuario.getCep());
			atualizar.setString(5, usuario.getRua());
			atualizar.setString(6, usuario.getBairro());
			atualizar.setString(7, usuario.getCidade());
			atualizar.setString(8, usuario.getUf());

			if (usuario.isAtualizarImage()) {
				atualizar.setString(9, usuario.getFotoBase64());
				atualizar.setString(10, usuario.getContentType());
			}

			if (usuario.isAtualizarPdf()) {
				atualizar.setString(11, usuario.getCurriculoBase64());
				atualizar.setString(12, usuario.getContentTypeCurriculo());
			}

			if (usuario.isAtualizarImage()) {
				atualizar.setString(13, usuario.getFotoBase64Miniatura());
			}

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

	public UsuarioBean consultar(Long id) throws Exception {

		UsuarioBean usuarioBean = null;

		String sql = "SELECT * FROM usuario WHERE id = " + id + " AND id <> 29 ";
		PreparedStatement consultar = connection.prepareStatement(sql);
		ResultSet resultSet = consultar.executeQuery();

		if (resultSet.next()) {

			usuarioBean = new UsuarioBean();
			usuarioBean.setId(resultSet.getLong("id"));
			usuarioBean.setNome(resultSet.getString("nome"));
			usuarioBean.setLogin(resultSet.getString("login"));
			usuarioBean.setSenha(resultSet.getString("senha"));
			usuarioBean.setCep(resultSet.getString("cep"));
			usuarioBean.setRua(resultSet.getString("rua"));
			usuarioBean.setBairro(resultSet.getString("bairro"));
			usuarioBean.setCidade(resultSet.getString("cidade"));
			usuarioBean.setUf(resultSet.getString("estado"));
			usuarioBean.setIbge(resultSet.getString("ibge"));
			usuarioBean.setFotoBase64(resultSet.getString("fotobase64"));
			usuarioBean.setContentType(resultSet.getString("contenttype"));
			usuarioBean.setCurriculoBase64(resultSet.getString("curriculobase64"));
			usuarioBean.setContentTypeCurriculo(resultSet.getString("contenttypecurriculo"));
			usuarioBean.setFotoBase64Miniatura(resultSet.getString("fotobase64miniatura"));
		}

		return usuarioBean;
	}

	public boolean validarLogin(String login) throws Exception {

		String sql = "SELECT COUNT(1) AS qtd FROM usuario WHERE login = '" + login + "' ";
		PreparedStatement consultar = connection.prepareStatement(sql);
		ResultSet resultSet = consultar.executeQuery();

		if (resultSet.next()) {

			return resultSet.getInt("qtd") <= 0;

		}

		return false;
	}

	public boolean validarLoginUpdate(String login, String id) throws Exception {

		String sql = "SELECT COUNT(1) AS qtd FROM usuario WHERE login = '" + login + "' AND id <> '" + id + "' ";
		PreparedStatement consultar = connection.prepareStatement(sql);
		ResultSet resultSet = consultar.executeQuery();

		if (resultSet.next()) {

			return resultSet.getInt("qtd") <= 0;

		}

		return false;
	}

	public boolean validarSenha(String senha) throws Exception {

		String sql = "SELECT COUNT(1) AS qtd FROM usuario WHERE senha = '" + senha + "' ";
		PreparedStatement consultar = connection.prepareStatement(sql);
		ResultSet resultSet = consultar.executeQuery();

		if (resultSet.next()) {

			return resultSet.getInt("qtd") <= 0;

		}

		return false;
	}

	public boolean validarSenhaUpdate(String senha, String id) throws Exception {

		String sql = "SELECT COUNT(1) AS qtd FROM usuario WHERE senha = '" + senha + "' AND id <> " + id;
		PreparedStatement consultar = connection.prepareStatement(sql);
		ResultSet resultSet = consultar.executeQuery();

		if (resultSet.next()) {

			return resultSet.getInt("qtd") <= 0;

		}

		return false;
	}

}
