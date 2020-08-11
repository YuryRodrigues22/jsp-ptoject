package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.TelefoneBean;
import beans.UsuarioBean;
import dao.TelefoneDAO;
import dao.UsuarioDAO;

/**
 * Servlet implementation class Telefone
 */
@WebServlet("/salvarTelefone")
public class Telefone extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UsuarioDAO usuarioDAO = new UsuarioDAO();
	private TelefoneDAO telefoneDAO = new TelefoneDAO();

	public Telefone() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String user = request.getParameter("user");
		String acao = request.getParameter("acao");

		try {

			if (acao.equalsIgnoreCase("addFone")) {

				Long id = Long.parseLong(user);
				UsuarioBean usuario = usuarioDAO.consultar(id);

				request.getSession().setAttribute("user", usuario);
				request.getSession().setAttribute("userEscolhido", usuario);

				RequestDispatcher view = request.getRequestDispatcher("/telefone.jsp");
				request.setAttribute("telefones", telefoneDAO.listar(usuario.getId()));
				request.setAttribute("userSelecionado", user);
				view.forward(request, response);

			} else if (acao.equalsIgnoreCase("delete")) {

				UsuarioBean usuario = (UsuarioBean) request.getSession().getAttribute("userEscolhido");
				String fone = request.getParameter("fone");
				Long foneId = Long.parseLong(fone);

				telefoneDAO.delete(foneId);

				RequestDispatcher view = request.getRequestDispatcher("/telefone.jsp");
				request.setAttribute("telefones", telefoneDAO.listar(usuario.getId()));
				request.setAttribute("userSelecionado", usuario.getId());
				request.setAttribute("msg", "Removido com Sucesso!");
				view.forward(request, response);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		UsuarioBean usuario = (UsuarioBean) request.getSession().getAttribute("userEscolhido");
		String numero = request.getParameter("telefone");
		String tipo = request.getParameter("tipo");
		String acao = request.getParameter("acao");

		try {

			if (acao == null || (acao != null && !acao.equalsIgnoreCase("voltar"))) {

				if (numero == null || numero.isEmpty()) {

					RequestDispatcher view = request.getRequestDispatcher("/telefone.jsp");
					request.setAttribute("telefones", telefoneDAO.listar(usuario.getId()));
					request.setAttribute("msg", "Informe o Numero!");
					view.forward(request, response);

				} else {

					TelefoneBean telefone = new TelefoneBean();
					telefone.setNumero(numero);
					telefone.setTipo(tipo);
					telefone.setUsuario(usuario.getId());

					telefoneDAO.salvar(telefone);

					request.getSession().setAttribute("userEscolhido", usuario);
					request.setAttribute("userEscolhido", usuario);

					RequestDispatcher view = request.getRequestDispatcher("/telefone.jsp");
					request.setAttribute("telefones", telefoneDAO.listar(usuario.getId()));
					request.setAttribute("msg", "Salvo com Sucesso!");
					view.forward(request, response);

				}

			} else {

				RequestDispatcher view = request.getRequestDispatcher("/cadastrousuario.jsp");
				request.setAttribute("usuarios", usuarioDAO.listar());
				view.forward(request, response);
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
