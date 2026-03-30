package com.api.test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

import java.io.IOException;

import org.testng.annotations.Test;

import com.api.constant.Roles;
import com.api.util.AuthTokenProvider;

import static com.api.util.ConfigManager.*;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.module.jsv.JsonSchemaValidator;

public class UserDetailsAPITest {

	@Test
	public void userDetailsAPITest() throws IOException {
		
		Header authHeader=new Header("Authorization",AuthTokenProvider.getToken(Roles.FD));
		

		
		given()
		.baseUri(readProperties("BASE_URI"))
		.contentType(ContentType.JSON)
		.accept(ContentType.ANY)
		.header(authHeader)
		.log().uri()
		.log().method()
		.log().body()
		.and()
		.when()
		.get("/userdetails")
		.then()
		.log().all()
		.statusCode(200)
		.time(lessThan(1000L))
		.and()
		//.body("data.id", equalTo(4))
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/userResponseSchema.json"));
	}
}
