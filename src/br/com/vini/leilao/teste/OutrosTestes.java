package br.com.vini.leilao.teste;

import static com.jayway.restassured.RestAssured.*;

import java.io.File;

import org.junit.Test;

public class OutrosTestes {
	
	@Test
	public void deveGerarUmCookie() {
		
		expect()
			.cookie("rest-assured", "funciona")
		.get("cookie/teste");
		
	}
	
	@Test
	public void deveGerarUmHeader() {
		expect()
			.header("novo-header", "abc")
		.get("cookie/teste");
		
	}
	
	@Test
	public void testaServicoQueRecebeArquivo() {
		given()
		.multiPart(new File("/path/para/arquivo"))
		.when()
			.post("/post");
	}

}
