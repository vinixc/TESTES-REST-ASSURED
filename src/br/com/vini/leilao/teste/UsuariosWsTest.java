package br.com.vini.leilao.teste;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.path.xml.XmlPath;

import br.com.vini.leilao.modelo.Usuario;

public class UsuariosWsTest {
	
	private Usuario mauricio;
	private Usuario guilherme;

	@Before
	public void setUp() {
		mauricio = new Usuario(1l,"Mauricio Aniche", "mauricio.aniche@caelum.com.br");
		guilherme = new Usuario(2l,"Guilherme Silveira", "guilherme.silveira@caelum.com.br");
	}
	
	@Test
	public void deveRetornarListaDeUsuario() {
		XmlPath xmlPath = 
				given().
				header("Accept", "application/xml").
				get("/usuarios").andReturn().xmlPath();
		
		List<Usuario> usuarios = xmlPath.getList("list.usuario",Usuario.class);
		
		assertEquals(mauricio, usuarios.get(0));
		assertEquals(guilherme, usuarios.get(1));
	}
	
	
	@Test
	public void deveRetornarUsuarioPeloId() {
		JsonPath jsonPath =
				given()
					.header("Accept", "application/json")
					.parameter("usuario.id", 1)
					.get("/usuarios/show")
					.andReturn().jsonPath();
		
		
		Usuario usuario = jsonPath.getObject("usuario", Usuario.class);
		
		//System.out.println(jsonPath.getString("usuario.nome"));
		
		assertEquals(usuario, mauricio);
	}

}
