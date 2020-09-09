<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix = "fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Cadastro de Produto</title>
<script src="resources\javascript\jquery.min.js" type="text/javascript"></script>
<script src="resources\javascript\jquery.maskMoney.min.js" type="text/javascript"></script>
<link rel="stylesheet" href="resources/css/cadastro.css">
</head>
<body>
<a href="acessoliberado.jsp"><img src="resources/img/home.png" width="40px" height="40px" title="Home"></img></a>
<a href="index.jsp"><img src="resources/img/logout.png" width="40px" height="40px" title="Sair"></img></a>
	<div align="center">
	<h1 style="font: 25px Lucida Sans Unicode, Lucida Grande, sans-serif">Cadastro de Produto</h1>
	<h3>${msg}</h3>
	<form action="salvarProduto" method="post" id="formProd" onsubmit="return validarCampos() ? true : false;">
		<ul class="form-style-1" style="margin-right: 50px">
			<li>
				<table>
					<tr>
						<td><input type="hidden" id="id" name="id" value="${prod.id}"></td>
					</tr>
					<tr>
						<td style="font: 13px Lucida Sans Unicode, Lucida Grande, sans-serif">Nome:</td>
						<td><input type="text" id="nome" name="nome" value="${prod.nome}"></td>
					</tr>
					<tr>
						<td style="font: 13px Lucida Sans Unicode, Lucida Grande, sans-serif">Quantidade:</td>
						<td><input type="number" id="quantidade" name="quantidade" maxlength="10" value="${prod.quantidade}"></td>
					</tr>
					<tr>
						<td style="font: 13px Lucida Sans Unicode, Lucida Grande, sans-serif">Preço R$:</td>
						<td><input type="text" id="preco" name="preco" step="0.01" maxlength="12" data-thousands="." data-decimal="," value="${prod.valorEmTexto}"></td>
					</tr>
				</table>
			</li>
		</ul>
			<ul class="form-style-1" style="margin-right: 50px">
				<li>
					<table>
						<tr>
							<td width="50px"></td>
							<td><input type="submit" value="Salvar"></td>
							<td><input type="submit" value="Cancelar" onclick="document.getElementById('formProd').action='salvarProduto?acao=reset'"></td>
						</tr>
					</table>
				</li>
			</ul>
	</form>
	</div>
	<br/>
	<div align="center">
	<h3 style="font: 25px Lucida Sans Unicode, Lucida Grande, sans-serif; margin: auto;">Produtos</h3>
	<ul class="form-style-1">
		<li>
			<table>
				<tr>
					<td align="center" height="15px" width="15px"><input type="text" disabled="disabled" value="ID" style="text-align: center; background-color: #87CEFA"/></td>
					<td align="center" height="15px" width="15px"><input type="text" disabled="disabled" value="NOME" style="text-align: center; background-color: #87CEFA"/></td>
					<td align="center" height="15px" width="15px"><input type="text" disabled="disabled" value="QTD" style="text-align: center; background-color: #87CEFA"/></td>
					<td align="center" height="15px" width="15px"><input type="text" disabled="disabled" value="PREÇO" style="text-align: center; background-color: #87CEFA"/></td>
					<td colspan="2" align="center" height="15px" width="15px"><input type="text" disabled="disabled" value="AÇÃO" style="text-align: center; background-color: #87CEFA"/></td>
				</tr>
				<c:forEach items="${produtos}" var="prod">
					<tr>
						<td width="15px" height="30px"><input type="text" height="30px" disabled="disabled" value="${prod.id}"/></td>
						<td width="15px" height="30px"><input type="text" height="30px" disabled="disabled" value="${prod.nome}"/></td>
						<td width="15px" height="30px"><input type="text" height="30px" disabled="disabled" value="${prod.quantidade}"/></td>
						<td width="15px" height="30px"><fmt:formatNumber type="number" maxFractionDigits="2" value="${prod.preco}" /></td>
						<td width="15px" height="30px" style="text-align: center"><a href="salvarProduto?acao=delete&prod=${prod.id}"><img src="resources/img/excluir-icon.png" width="20px" height="20px" title="Excluir"></a></td>
						<td width="15px" height="30px" style="text-align: center"><a href="salvarProduto?acao=editar&prod=${prod.id}"><img src="resources/img/editar-icon.png" width="20px" height="20px" title="Editar"></a></td>	
					</tr>
				</c:forEach>
			</table>
		</li>
	</ul>
	</div>
</body>
<script type="text/javascript">


	function validarCampos() {

		var nome = document.getElementById("nome").value;
		var qtd = document.getElementById("quantidade").value;
		var preco = document.getElementById("preco").value;

		if (nome == null || nome == '') {
			alert('Informe o Nome');
			return false;
		} else if (qtd == null || qtd == '') {
			alert('Informe a Quantidade');
			return false;
		} else if (preco == null || preco == '') {
			alert('Informe o Preço');
			return false;
		}

		return true;

	}
	
	$(function(){
		$('#preco').maskMoney();
	})
	
</script>
</html>