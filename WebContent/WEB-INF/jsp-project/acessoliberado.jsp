<jsp:useBean id="calcula" class="beans.UsuarioBean" type="beans.UsuarioBean" scope="page"/>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

<html>
	<head>
		<meta http-equiv="Content-type" content="text/html; charset=ISO-8859-1">
		<title></title>
	</head>
	<body>
		<div align="center" style="padding-top: 10%">
			<h1>Seja bem vindo</h1>
			<table>
				<tr>
					<td><a href="salvarUsuario?acao=listartodos"><img src="resources/img/usuario.jpg" width="100px" height="100px" title="Cadastro de Usuario"></a></td>
					<td><a href="salvarProduto?acao=listartodos"><img src="resources/img/produto.png" width="100px" height="100px" title="Cadastro de Produto"></a></td>
				</tr>
				<tr>
					<td>Cad.Usuario</td>
					<td>Cad.Produto</td>
				</tr>
			</table>
		</div>
	</body>
</html>