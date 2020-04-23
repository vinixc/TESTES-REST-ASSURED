package br.com.vini.leilao.teste;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.jayway.restassured.path.xml.XmlPath;

import br.com.vini.leilao.modelo.Usuario;

public class UsuariosWsTest {
	
	@Test
	public void deveRetornarListaDeUsuario() {
		XmlPath xmlPath = 
				given().
				header("Accept", "application/xml").
				get("/usuarios").andReturn().xmlPath();
		
		List<Usuario> usuarios = xmlPath.getList("list.usuario",Usuario.class);
		
		Usuario esperado1 = new Usuario(1l,"Mauricio Aniche", "mauricio.aniche@caelum.com.br");
		Usuario esperado2 = new Usuario(2l,"Guilherme Silveira", "guilherme.silveira@caelum.com.br");
		
		assertEquals(esperado1, usuarios.get(0));
		assertEquals(esperado2, usuarios.get(1));
	}

}
