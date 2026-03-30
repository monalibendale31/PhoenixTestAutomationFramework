package com.api.test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

import org.testng.annotations.Test;

import com.api.pojo.UserCredentials;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;

public class LoginAPITest {
	
	@Test
	public void validateLoginAPITest() {
	
		UserCredentials usercred=new UserCredentials("iamfd","password");
		
		given()
		.baseUri("http://64.227.160.186:9000/v1")
		.and()
		.contentType(ContentType.JSON)
		.accept(ContentType.ANY)
		.body(usercred)
		.log().uri()
		.log().headers()
		.log().method()
		.when()
		.post("login")
		.then()
		.log().all()
		.statusCode(200)
		.and()
		.body("message",equalTo("Success"))
		.and()
		.time(lessThan(1000L))
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/loginResponseSchema.json"));
		
	}

}
