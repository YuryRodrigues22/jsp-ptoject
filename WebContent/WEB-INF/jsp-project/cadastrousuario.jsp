<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"
            integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
            crossorigin="anonymous"></script>
<meta charset="ISO-8859-1">
<title>Cadastro de Usuário</title>
<link rel="stylesheet" href="resources/css/cadastro.css">
</head>
<body>
<a href="acessoliberado.jsp"><img src="resources/img/home.png" width="40px" height="40px" title="Home"></img></a>
<a href="index.jsp"><img src="resources/img/logout.png" width="40px" height="40px" title="Sair"></img></a>
	<div align="center">
	<h1 style="font: 25px Lucida Sans Unicode, Lucida Grande, sans-serif">Cadastro de Usuário</h1>
	<h3>${msg}</h3>
	<form action="salvarUsuario" method="post" id="formUser" onsubmit="return validarCampos() ? true : false;" enctype="multipart/form-data">
		<ul class="form-style-1" style="margin-right: 50px">
			<li>
				<table>
					<tr>
						<td><input type="hidden" id="id" name="id" value="${user.id}"></td>
						<td><input type="hidden" id ="ibge" name="ibge" value="${user.ibge}"></td>
					</tr>
					<tr>
						<td style="font: 13px Lucida Sans Unicode, Lucida Grande, sans-serif">Nome:</td>
						<td><input type="text" id="nome" name="nome" maxlength="50" value="${user.nome}"></td>
					</tr>
					<tr>
						<td style="font: 13px Lucida Sans Unicode, Lucida Grande, sans-serif">Login:</td>
						<td><input type="text" id="login" name="login" maxlength="10" value="${user.login}"></td>
						<td style="font: 13px Lucida Sans Unicode, Lucida Grande, sans-serif">Senha:</td>
						<td><input type="password" id="senha" name="senha" maxlength="10" value="${user.senha}"></td>
					</tr>
					<tr>
						<td style="font: 13px Lucida Sans Unicode, Lucida Grande, sans-serif">Cep:</td>
						<td><input type="text" id="cep" name="cep" onblur="consultaCep();" maxlength="9" value="${user.cep}"></td>
						<td style="font: 13px Lucida Sans Unicode, Lucida Grande, sans-serif">Rua:</td>
						<td><input type="text" id ="rua" name="rua" maxlength="50" value="${user.rua}"></td>
					</tr>
					<tr>
						<td style="font: 13px Lucida Sans Unicode, Lucida Grande, sans-serif">Bairro:</td>
						<td><input type="text" id ="bairro" name="bairro" maxlength="50" value="${user.bairro}"></td>
						<td style="font: 13px Lucida Sans Unicode, Lucida Grande, sans-serif">Cidade:</td>
						<td><input type="text" id ="cidade" name="cidade" maxlength="50" value="${user.cidade}"></td>
					</tr>
					<tr>
						<td style="font: 13px Lucida Sans Unicode, Lucida Grande, sans-serif">Estado:</td>
						<td><input type="text" id ="uf" name="uf" maxlength="30" value="${user.uf}"></td>
					</tr>
					<tr>
						<td style="font: 13px Lucida Sans Unicode, Lucida Grande, sans-serif">Foto:</td>
						<td>
							<input type="file" id="foto" name="foto">
							<input type="hidden" name="fotoTemp" readonly="readonly" value="${user.fotoBase64}"/> 
							<input type="hidden" name="contentTypeTemp" readonly="readonly" value="${user.contentType}"/>
						</td>
					</tr>
					<tr>
						<td style="font: 13px Lucida Sans Unicode, Lucida Grande, sans-serif">Curriculo:</td>
						<td>
							<input type="file" id="curriculo" name="curriculo"/>
							<input type="hidden" name="curriculoTemp" readonly="readonly" value="${user.curriculoBase64}"/>
							<input type="hidden" name="contentTypeCurriculoTemp" readonly="readonly" value="${user.contentTypeCurriculo}"/>
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
							<td><input type="submit" value="Cancelar" onclick="document.getElementById('formUser').action='salvarUsuario?acao=reset'"></td>
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
					<td align="center" height="15px" width="5px"><input type="text" disabled="disabled" value="ID" style="text-align: center;"/></td>
					<td align="center" height="15px" width="10px"><input type="text" disabled="disabled" value="NOME" style="text-align: center;"/></td>
					<td align="center" height="15px" width="10px"><input type="text" disabled="disabled" value="LOGIN" style="text-align: center;"/></td>
					<td align="center" height="15px" width="10px"><input type="text" disabled="disabled" value="IMAGEM" style="text-align: center;"/></td>
					<td align="center" height="15px" width="10px"><input type="text" disabled="disabled" value="CURRICULO" style="text-align: center;"/></td>
					<td colspan="3" align="center" height="15px" width="15px"><input type="text" disabled="disabled" value="AÇÃO" style="text-align: center;"/></td>
				</tr>
				<c:forEach items="${usuarios}" var="user">
					<tr>
						<td><input type="text" width="5px" height="30px" disabled="disabled" value="${user.id}"/></td>
						<td><input type="text" height="30px" disabled="disabled" value="${user.nome}"/></td>
						<td><input type="text" height="30px" disabled="disabled" value="${user.login}"/></td>
						<c:if test="${user.fotoBase64Miniatura.isEmpty() == false || user.fotoBase64Miniatura != null}">
							<td style="text-align: center;"><a href="salvarUsuario?acao=download&tipo=imagem&user=${user.id}"><img src="${user.fotoBase64Miniatura}" alt="Imagem User" title="Imagem User" width="30px" height="30px"/></a></td>
						</c:if>
						<c:if test="${user.fotoBase64Miniatura.isEmpty() == true || user.fotoBase64Miniatura == null}">
							<td style="text-align: center;"><img alt="Imagem Padrao" width="20px" height="20px" src="resources/img/iconHome.png" onclick="alert('Não Possui Foto');"/></td>
						</c:if>
						<c:if test="${user.curriculoBase64.isEmpty() == false || user.curriculoBase64 != null}">
							<td style="text-align: center;"><a href="salvarUsuario?acao=download&tipo=pdf&user=${user.id}"><img src="resources/img/curriculo.png" alt="Curriculo" title="Curriculo" width="30px" height="30px"/></a></td>
						</c:if>
						<c:if test="${user.curriculoBase64.isEmpty() == true || user.curriculoBase64 == null}">
							<td style="text-align: center;"><img src="resources/img/semcurriculo.png" alt="Curriculo" title="Curriculo" width="30px" height="30px" onclick="alert('Não Possui Curriculo');"/></td>
						</c:if>
						<td width="5px" height="30px" style="text-align: center"><a href="salvarUsuario?acao=delete&user=${user.id}"><img src="resources/img/excluir-icon.png" width="20px" height="20px" title="Excluir"></a></td>
						<td width="5px" height="30px" style="text-align: center"><a href="salvarUsuario?acao=editar&user=${user.id}"><img src="resources/img/editar-icon.png" width="20px" height="20px" title="Editar"></a></td>	
						<td width="5px" height="30px" style="text-align: center"><a href="salvarTelefone?acao=addFone&user=${user.id}"><img src="resources/img/telefone.png" width="20px" height="20px" title="Telefones"></a></td>
					</tr>
				</c:forEach>
			</table>
		</li>
	</ul>
	</div>
	<script>
	
	function validarCampos(){
		var nome = document.getElementById("nome").value;
		var login = document.getElementById("login").value;
		var senha = document.getElementById("senha").value;
		
		if(nome == null || nome == ''){
			alert('Informe o Nome');
			return false;
		}else if(login == null || login == ''){
			alert('Informe o Login');
			return false;
		}else if(senha == null || senha == ''){
			alert('Informe a Senha');
			return false;
		}
		
		return true;
	}
	
	function consultaCep() {
		var cep = $("#cep").val();
	
        //Consulta o webservice viacep.com.br/
        $.getJSON("https://viacep.com.br/ws/"+ cep +"/json/?callback=?", function(dados) {

            if (!("erro" in dados)) {
            	
	            $("#rua").val(dados.logradouro);
	            $("#bairro").val(dados.bairro);
	            $("#cidade").val(dados.localidade);
	            $("#uf").val(dados.uf);
	            $("#ibge").val(dados.ibge);
               
            }
            else {
                limpa_formulário_cep();
                alert("CEP não encontrado.");
            }
        });
	}
	
</script>
</body>
</html>