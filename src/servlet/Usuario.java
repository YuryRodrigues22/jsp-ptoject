package servlet;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.codec.binary.Base64;

import beans.UsuarioBean;
import dao.UsuarioDAO;

@WebServlet("/salvarUsuario")
@MultipartConfig
public class Usuario extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UsuarioDAO usuarioDAO = new UsuarioDAO();

	public Usuario() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			String acao = request.getParameter("acao") != null ? request.getParameter("acao") : "listartodos";
			String user = request.getParameter("user");

			if (acao != null && acao.equalsIgnoreCase("delete")) {

				Long id = Long.parseLong(user);
				usuarioDAO.delete(id);
				RequestDispatcher view = request.getRequestDispatcher("/cadastrousuario.jsp");
				request.setAttribute("usuarios", usuarioDAO.listar());
				view.forward(request, response);

			} else if (acao != null && acao.equalsIgnoreCase("editar")) {

				Long id = Long.parseLong(user);
				UsuarioBean usuarioBean = usuarioDAO.consultar(id);
				RequestDispatcher view = request.getRequestDispatcher("/cadastrousuario.jsp");
				request.setAttribute("user", usuarioBean);
				request.setAttribute("usuarios", usuarioDAO.listar());
				view.forward(request, response);

			} else if (acao != null && acao.equalsIgnoreCase("listartodos")) {

				RequestDispatcher view = request.getRequestDispatcher("/cadastrousuario.jsp");
				request.setAttribute("usuarios", usuarioDAO.listar());
				view.forward(request, response);

			} else if (acao != null && acao.equalsIgnoreCase("download")) {

				Long id = Long.parseLong(user);
				UsuarioBean usuario = usuarioDAO.consultar(id);

				if (usuario != null) {
					String contentType = "";
					byte[] fileBytes = null;
					String tipo = request.getParameter("tipo");

					/* Converte a base64 da imagem do banco para byte[] */
					if (tipo.equalsIgnoreCase("imagem")) {

						contentType = usuario.getContentType();
						fileBytes = new Base64().decodeBase64(usuario.getFotoBase64());

					} else if (tipo.equalsIgnoreCase("pdf")) {

						contentType = usuario.getContentTypeCurriculo();
						fileBytes = new Base64().decodeBase64(usuario.getCurriculoBase64());
					}

					response.setHeader("Content-Disposition",
							"attachment;filename=arquivo." + contentType.split("\\/")[1]);

					/* Coloca os bytes em um objeto de entrada */
					InputStream is = new ByteArrayInputStream(fileBytes);

					/* Inicio da resposta */
					int read = 0;
					byte[] bytes = new byte[1024];
					OutputStream os = response.getOutputStream();

					while ((read = is.read(bytes)) != -1) {
						os.write(bytes, 0, read);
					}

					os.flush();
					os.close();

				}

				RequestDispatcher view = request.getRequestDispatcher("/cadastrousuario.jsp");
				request.setAttribute("usuarios", usuarioDAO.listar());
				view.forward(request, response);

			} else {

				RequestDispatcher view = request.getRequestDispatcher("/cadastrousuario.jsp");
				request.setAttribute("usuarios", usuarioDAO.listar());
				view.forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String acao = request.getParameter("acao");
		if (acao != null && acao.equalsIgnoreCase("reset")) {
			try {

				RequestDispatcher view = request.getRequestDispatcher("/cadastrousuario.jsp");
				request.setAttribute("usuarios", usuarioDAO.listar());
				view.forward(request, response);

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {

			String id = request.getParameter("id");
			String nome = request.getParameter("nome").toLowerCase();
			String login = request.getParameter("login").toLowerCase();
			String senha = request.getParameter("senha");
			String cep = request.getParameter("cep");
			String rua = request.getParameter("rua");
			String bairro = request.getParameter("bairro");
			String cidade = request.getParameter("cidade");
			String uf = request.getParameter("uf");
			String ibge = request.getParameter("ibge");

			UsuarioBean usuario = new UsuarioBean();
			usuario.setId(!id.isEmpty() ? Long.parseLong(id) : null);
			usuario.setNome(nome);
			usuario.setLogin(login);
			usuario.setSenha(senha);
			usuario.setCep(cep);
			usuario.setRua(rua);
			usuario.setBairro(bairro);
			usuario.setCidade(cidade);
			usuario.setUf(uf);
			usuario.setIbge(ibge);

			try {

				/* File Upload de Imagem */
				if (ServletFileUpload.isMultipartContent(request)) {

					Part imagemFoto = request.getPart("foto");

					if (imagemFoto != null && imagemFoto.getInputStream().available() > 0) {

						String fotoBase64 = new Base64().encodeBase64String(converteStreamParaByte(imagemFoto.getInputStream()));

						usuario.setFotoBase64(fotoBase64);
						usuario.setContentType(imagemFoto.getContentType());

						/* Inicio Miniatura Imagem */

						/* Transforma em um bufferedImage */
						byte[] imageByteDecode = new Base64().decodeBase64(fotoBase64);
						BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageByteDecode));

						/* Pegar o tipo da Imagem */
						int type = bufferedImage.getType();

						if (type == 0) {
							type = BufferedImage.TYPE_INT_ARGB;
						}

						/* Criar Miniatura */
						BufferedImage resizedImage = new BufferedImage(100, 100, type);
						Graphics2D g = resizedImage.createGraphics();
						g.drawImage(bufferedImage, 0, 0, 100, 100, null);
						g.dispose();

						/* Escrever Imagem */
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ImageIO.write(resizedImage, "png", baos);

						String miniaturaBase64 = "data:image/png;base64,"
								+ DatatypeConverter.printBase64Binary(baos.toByteArray());

						usuario.setFotoBase64Miniatura(miniaturaBase64);

						/* Fim Miniatura Imagem */

					} else {
						usuario.setAtualizarImage(false);
					}

					/* Processa PDF */
					Part curriculoPdf = request.getPart("curriculo");
					if (curriculoPdf != null && curriculoPdf.getInputStream().available() > 0) {

						String curriculoBase64 = new Base64()
								.encodeBase64String(converteStreamParaByte(curriculoPdf.getInputStream()));
						usuario.setCurriculoBase64(curriculoBase64);
						usuario.setContentTypeCurriculo(curriculoPdf.getContentType());

					} else {

						usuario.setCurriculoBase64(request.getParameter("curriculoTemp"));
						usuario.setContentTypeCurriculo(request.getParameter("contentTypeCurriculoTemp"));

						if (usuario.getCurriculoBase64() == null || usuario.getCurriculoBase64().isEmpty()) {

							usuario.setContentTypeCurriculo(null);
							usuario.setCurriculoBase64(null);
						}
					}
				}

				/* FIM File Upload de Imagem */

				String msg = null;
				boolean podeInserir = true;

				if (nome == null || nome.isEmpty()) {
					msg = "Nome deve ser informado";
					request.setAttribute("msg", msg);
					podeInserir = false;
				} else if (login == null || login.isEmpty()) {
					msg = "Login deve ser informado";
					request.setAttribute("msg", msg);
					podeInserir = false;
				} else if (senha == null || senha.isEmpty()) {
					msg = "Senha deve ser informado";
					request.setAttribute("msg", msg);
					podeInserir = false;
				} else {

					if (id == null || id.isEmpty() && !usuarioDAO.validarLogin(login)) {
						msg = "Usuário já existente!";
						podeInserir = false;

					} else if (id == null || id.isEmpty() && !usuarioDAO.validarSenha(senha)) {
						msg = "Senha já existente!";
						podeInserir = false;
					}

					if (id == null || id.isEmpty() && usuarioDAO.validarLogin(login) && usuarioDAO.validarSenha(senha)
							&& podeInserir) {
						usuarioDAO.salvar(usuario);

					} else if (id != null && !id.isEmpty() && podeInserir) {
						if (!usuarioDAO.validarLoginUpdate(login, id)) {
							msg = "Login já existe para outro usuário";
							podeInserir = false;

						} else if (!usuarioDAO.validarSenhaUpdate(senha, id)) {
							msg = "Senha já existe para outro usuário";
							podeInserir = false;

						} else {
							usuarioDAO.atualizar(usuario);
						}
					}
				}

				if (!podeInserir) {
					request.setAttribute("user", usuario);
				}

				if (msg != null) {
					request.setAttribute("msg", msg);
				}

				RequestDispatcher view = request.getRequestDispatcher("/cadastrousuario.jsp");
				request.setAttribute("usuarios", usuarioDAO.listar());
				view.forward(request, response);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/* Converte a entrada de Fluxo de dados da imagem para um Array de Byte */
	private byte[] converteStreamParaByte(InputStream imagem) throws Exception {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int tamanho = imagem.read();
		while (tamanho != -1) {
			baos.write(tamanho);
			tamanho = imagem.read();
		}

		return baos.toByteArray();
	}
}
