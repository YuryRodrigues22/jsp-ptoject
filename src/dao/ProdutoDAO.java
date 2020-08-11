package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.ProdutoBean;
import connection.SingleConnetion;

public class ProdutoDAO {

	private Connection connection;

	public ProdutoDAO() {
		connection = SingleConnetion.getConnection();
	}

	public void salvar(ProdutoBean produto) {

		try {

			String sql = " INSERT INTO produto (nome, quantidade, preco) VALUES (?, ?, ?) ";
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setString(1, produto.getNome());
			insert.setLong(2, produto.getQuantidade());
			insert.setDouble(3, produto.getPreco());
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

	public List<ProdutoBean> listar() {

		List<ProdutoBean> list = new ArrayList<ProdutoBean>();

		try {

			String sql = " SELECT * FROM produto ORDER BY id ";
			PreparedStatement lista = connection.prepareStatement(sql);
			ResultSet resultSet = lista.executeQuery();

			while (resultSet.next()) {
				ProdutoBean produto = new ProdutoBean();
				produto.setId(resultSet.getLong("id"));
				produto.setNome(resultSet.getString("nome"));
				produto.setQuantidade(resultSet.getLong("quantidade"));
				produto.setPreco(resultSet.getDouble("preco"));
				list.add(produto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	public void delete(Long id) {
		
		try {
		
		String sql = " DELETE FROM produto WHERE id = " + id;
		PreparedStatement deletar = connection.prepareStatement(sql);
		deletar.execute();
		connection.commit();
		
		}catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public void editar(ProdutoBean produto) {
		
		try {
		
		String sql = " UPDATE produto SET nome = ?, quantidade = ?, preco = ? WHERE id = " + produto.getId();
		PreparedStatement atualizar = connection.prepareStatement(sql);
		atualizar.setString(1, produto.getNome());
		atualizar.setLong(2, produto.getQuantidade());
		atualizar.setDouble(3, produto.getPreco());
		atualizar.executeUpdate();
		connection.commit();
		
		}catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public ProdutoBean consultar(Long id) throws Exception {
		
		ProdutoBean produto = null;
		
		String sql = " SELECT * FROM produto WHERE id = " + id;
		PreparedStatement consultar = connection.prepareStatement(sql);
		ResultSet resultSet = consultar.executeQuery();
		
		if(resultSet.next()) {
			
			produto = new ProdutoBean();
			produto.setId(resultSet.getLong("id"));
			produto.setNome(resultSet.getString("nome"));
			produto.setPreco(resultSet.getDouble("preco"));
			produto.setQuantidade(resultSet.getLong("quantidade"));
			
		}
		
		return produto;
	}
	
	public boolean validarNome(String nome) throws Exception {

		String sql = " SELECT COUNT(1) AS qtd FROM produto WHERE nome = '" + nome + "' ";
		PreparedStatement consultar = connection.prepareStatement(sql);
		ResultSet resultSet = consultar.executeQuery();

		if (resultSet.next()) {

			return resultSet.getInt("qtd") <= 0;

		}

		return false;
	}
	
	public boolean validarNomeUpdate(String nome, String id) throws Exception {

		String sql = " SELECT COUNT(1) AS qtd FROM produto WHERE nome = '" + nome + "' AND id <> '" + id + "' ";
		PreparedStatement consultar = connection.prepareStatement(sql);
		ResultSet resultSet = consultar.executeQuery();

		if (resultSet.next()) {

			return resultSet.getInt("qtd") <= 0;

		}

		return false;
	}

}
