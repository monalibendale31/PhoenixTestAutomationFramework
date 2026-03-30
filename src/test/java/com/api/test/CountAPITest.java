package com.api.test;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.api.constant.Roles;
import com.api.util.AuthTokenProvider;
import com.api.util.ConfigManager;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;

public class CountAPITest {

	@Test
	public void verifyCountAPIResponse() {
		
		RestAssured.given()
		.baseUri(ConfigManager.readProperties("BASE_URI"))
		.and()
		.header("Authorization", AuthTokenProvider.getToken(Roles.FD))
		.when()
		.get("/dashboard/count")
		.then()
		.statusCode(200)
		.body("message", Matchers.equalTo("Success"))
		.time(Matchers.lessThan(2000L))
		.body("data", Matchers.notNullValue())
		.body("data.size()", Matchers.equalTo(3))
		.body("data.count", Matchers.everyItem(Matchers.greaterThanOrEqualTo(0)))
		.body("data.label",Matchers.everyItem(Matchers.not(Matchers.blankOrNullString())))
		.body("data.key", Matchers.containsInAnyOrder("pending_for_delivery","pending_fst_assignment","created_today"))
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/CountAPIResponse.json"))
		.log().all();
	}
	
	@Test
	public void countAPITest_MissingAuthToken() {
		RestAssured.given()
		.baseUri(ConfigManager.readProperties("BASE_URI"))
		.contentType(ContentType.JSON)
		//.header("Authorization",AuthTokenProvider.getToken(Roles.FD))
		.log().uri()
		.log().method()
		.log().headers()
		.when().get("/dashboard/count")
		.then()
		.log().all()
		.statusCode(401);
	}
	
}
