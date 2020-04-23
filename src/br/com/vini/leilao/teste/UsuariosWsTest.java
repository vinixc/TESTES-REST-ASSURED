package br.com.vini.leilao.teste;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.jayway.restassured.RestAssured;
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
		
		//Esse é o padrao de URI e PORT - Caso o Serviço esteja em outro lugar configurar nessas variaveis.
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
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
	
	@Test
	public void deveRetornarQuantidadeTotalLeiloes() {
		
		XmlPath xmlPath = given()
			.header("Accept", "application/xml")
			.get("/leiloes/total")
			.andReturn().xmlPath();
		
		int total = xmlPath.getInt("int");
		
		assertEquals(2, total);
		
	}
	
	@Test
	public void deveAdicionarUmUsuario() {
		Usuario joao = new Usuario("Joao da Silva", "joao@dasilva.com");
		
		XmlPath path = given()
			.header("Accept", "application/xml")
			.contentType("application/xml")
			.body(joao)
		.expect()
			.statusCode(200)
		.when()
			.post("/usuarios")
		.andReturn()
			.xmlPath();
		
		Usuario resposta = path.getObject("usuario", Usuario.class);
		
		assertEquals("Joao da Silva", resposta.getNome());
		assertEquals("joao@dasilva.com", resposta.getEmail());
	}

}
