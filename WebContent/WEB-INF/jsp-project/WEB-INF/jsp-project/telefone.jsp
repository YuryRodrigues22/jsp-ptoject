<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Cadastro de Telefone</title>
<link rel="stylesheet" href="resources/css/cadastro.css">
</head>
<body>
<a href="acessoliberado.jsp"><img src="resources/img/home.png" width="40px" height="40px" title="Home"></img></a>
<a href="index.jsp"><img src="resources/img/logout.png" width="40px" height="40px" title="Sair"></img></a>
	<div align="center">
	<h1 style="font: 25px Lucida Sans Unicode, Lucida Grande, sans-serif">Cadastro de Telefone</h1>
	<h3>${msg}</h3>
	<form action="salvarTelefone" method="post" id="formTel" onsubmit="return validarCampos() ? true : false;">
		<ul class="form-style-1" style="margin-right: 50px">
			<li>
				<table>
					<tr>
						<td style="font: 13px Lucida Sans Unicode, Lucida Grande, sans-serif">Usuario:</td>
						<td><input disabled="disabled" type="text" id="usuario" name="usuario" value="${userEscolhido.nome}"></td>
					</tr>
					<tr>
						<td style="font: 13px Lucida Sans Unicode, Lucida Grande, sans-serif">Telefone:</td>
						<td><input type="text" id="telefone" name="telefone" placeholder="(99)9 9999-9999" maxlength="15" onkeydown="mascara(this)" onkeyup="mascara(this)" value="${telefone.telefone}"></td>
						<td>	
							<select id="tipo" name="tipo">
								<option>Residencial</option>
								<option>Celular</option>
							</select>
						</td>
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
							<td><input type="submit" value="Voltar" onclick="document.getElementById('formTel').action = 'salvarTelefone?acao=voltar'"></td>
						</tr>
					</table>
				</li>
			</ul>
	</form>
	</div>
	<br/>
	<div align="center">
	<h3 style="font: 25px Lucida Sans Unicode, Lucida Grande, sans-serif; margin: auto;">Usuários</h3>
		<ul class="form-style-1">
			<li>
				<table>
					<tr>
					<td align="center" height="15px" width="5px"><input type="text" disabled="disabled" value="ID" style="text-align: center; background-color: #87CEFA"/></td>
					<td align="center" height="15px" width="5px"><input type="text" disabled="disabled" value="TELEFONE" style="text-align: center; background-color: #87CEFA"/></td>
					<td align="center" height="15px" width="5px"><input type="text" disabled="disabled" value="TIPO" style="text-align: center; background-color: #87CEFA"/></td>
					<td align="center" height="15px" width="5px"><input type="text" disabled="disabled" value="USUARIO" style="text-align: center; background-color: #87CEFA"/></td>
					<td colspan="2" align="center" height="15px" width="15px"><input type="text" disabled="disabled" value="AÇÃO" style="text-align: center; background-color: #87CEFA"/></td>
						
					</tr>
					<c:forEach items="${telefones}" var="fone">
						<tr>
							<td width="10px" height="30px"><input type="text" height="30px" disabled="disabled" value="${fone.id}"/></td>
							<td width="10px" height="30px"><input type="text" height="30px" disabled="disabled" value="${fone.numero}"/></td>
							<td width="10px" height="30px"><input type="text" height="30px" disabled="disabled" value="${fone.tipo}"/></td>
							<td width="10px" height="30px"><input type="text" height="30px" disabled="disabled" value="${fone.usuario}"/></td>
							<td width="5px" height="30px" style="text-align: center"><a href="salvarTelefone?acao=delete&fone=${fone.id}"><img src="resources/img/excluir-icon.png" width="20px" height="20px" title="Excluir"></a></td>
							<td width="15px" height="30px" style="text-align: center"><a href="salvarTelefone?acao=editar&fone=${fone.id}"><img src="resources/img/editar-icon.png" width="20px" height="20px" title="Edit"></a></td>	
						</tr>
					</c:forEach>
				</table>
			</li>
		</ul>
	</div>
	<script type="text/javascript">
	stop = '';
	function mascara( campo ) {
	        campo.value = campo.value.replace( /[^\d]/g, '' )
	                                 .replace( /^(\d\d)(\d)/, '($1) $2' )
	                                 .replace( /(\d{5})(\d)/, '$1-$2' );
	        if ( campo.value.length > 15 ) campo.value = stop;
	        else stop = campo.value;    
	}
	
	function validarCampos() {
		var numero = document.getElementById("numero").value == '';
		if(numero == '' || numero == null){
			alert('Informe o Número');
			return false
		}
		return true
	}
	
	
	</script>
	
</body>
</html>