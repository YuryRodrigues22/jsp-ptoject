package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.ProdutoBean;
import dao.ProdutoDAO;

@WebServlet("/salvarProduto")
public class Produto extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ProdutoDAO produtoDAO = new ProdutoDAO();

	public Produto() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			String acao = request.getParameter("acao");
			String prod = request.getParameter("prod");

			if (acao.equalsIgnoreCase("delete")) {

				Long id = Long.parseLong(prod);
				produtoDAO.delete(id);
				RequestDispatcher view = request.getRequestDispatcher("/cadastroproduto.jsp");
				request.setAttribute("produtos", produtoDAO.listar());
				view.forward(request, response);

			} else if (acao.equalsIgnoreCase("editar")) {

				Long id = Long.parseLong(prod);
				ProdutoBean produtoBean = produtoDAO.consultar(id);
				RequestDispatcher view = request.getRequestDispatcher("/cadastroproduto.jsp");
				request.setAttribute("prod", produtoBean);
				request.setAttribute("produtos", produtoDAO.listar());
				view.forward(request, response);

			} else if (acao.equalsIgnoreCase("listartodos")) {

				RequestDispatcher view = request.getRequestDispatcher("/cadastroproduto.jsp");
				request.setAttribute("produtos", produtoDAO.listar());
				view.forward(request, response);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String id = request.getParameter("id");
		String nome = request.getParameter("nome").toLowerCase();
		String qtd = request.getParameter("quantidade");
		String preco = request.getParameter("preco");
		String acao = request.getParameter("acao");

		try {

			if (acao != null && acao.equalsIgnoreCase("reset")) {

				RequestDispatcher view = request.getRequestDispatcher("/cadastroproduto.jsp");
				request.setAttribute("produtos", produtoDAO.listar());
				view.forward(request, response);

			}

			ProdutoBean produto = new ProdutoBean();
			produto.setId(!id.isEmpty() ? Long.parseLong(id) : null);
			produto.setNome(nome);

			String msg = null;
			boolean podeInserir = true;

			if (nome == null || nome.isEmpty()) {
				msg = "Nome deve ser informado";
				request.setAttribute("msg", msg);
				podeInserir = false;
			} else if (qtd == null || qtd.isEmpty()) {
				msg = "Quantidade deve ser informado";
				request.setAttribute("msg", msg);
				podeInserir = false;
			} else if (preco == null || preco.isEmpty()) {
				msg = "Preço deve ser informado";
				request.setAttribute("msg", msg);
				podeInserir = false;
			} else {

				if (id == null || id.isEmpty() && !produtoDAO.validarNome(nome)) {
					msg = "Produto já existente!";
					podeInserir = false;
				}
				
				if(qtd != null || !qtd.isEmpty()){
					produto.setQuantidade(Long.parseLong(qtd));
				}
				
				if(preco != null || !preco.isEmpty()) {
					
					String valorProd = preco.replace(".", "");
					valorProd = valorProd.replace(",", ".");
					produto.setPreco(Double.parseDouble(valorProd));
				}	
				
				if (id == null || id.isEmpty() && produtoDAO.validarNome(nome) && podeInserir) {
					produtoDAO.salvar(produto);

				} else if (id != null && !id.isEmpty() && podeInserir) {
					if (!produtoDAO.validarNomeUpdate(nome, id)) {
						msg = "Nome do Produtor já existe!";
						podeInserir = false;

					} else if (podeInserir) {
						produtoDAO.editar(produto);

					}
				}
			}
			if (!podeInserir) {
				request.setAttribute("prod", produto);
			}

			if (msg != null) {
				request.setAttribute("msg", msg);
			}

			RequestDispatcher view = request.getRequestDispatcher("/cadastroproduto.jsp");
			request.setAttribute("produtos", produtoDAO.listar());
			view.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
