package com.api.test;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.api.constant.Roles;
import com.api.util.AuthTokenProvider;
import com.api.util.ConfigManager;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;

public class MasterAPIRequest {

	@Test
	public void masterAPIRequest() {
		RestAssured.given()
		.baseUri(ConfigManager.readProperties("BASE_URI"))
		.and()
		.header("Authorization",AuthTokenProvider.getToken(Roles.FD))
		.and()
		.contentType("")
		.log().all()
		.when()
		.post("/master")
		.then()
		.log().all()
		.statusCode(200)
		.time(Matchers.lessThan(1000L))
		.body("message", Matchers.equalTo("Success"))
		.body("data", Matchers.notNullValue())
		.body("data", Matchers.hasKey("mst_oem"))
		.body("data", Matchers.hasKey("mst_action_status"))
		.body("$", Matchers.hasKey("message"))
		.body("data.mst_oem.id",Matchers.notNullValue())
		.body("data.mst_oem.size()", Matchers.equalTo(2))
		.body("data.mst_model.size()",Matchers.greaterThan(0))
		.body("data.mst_oem.name", Matchers.everyItem(Matchers.notNullValue()))
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/MasterAPIResponse.json"));	
	}
	
	@Test
	public void invalidTokenMasterAPITest() {
		RestAssured.given()
		.baseUri(ConfigManager.readProperties("BASE_URI"))
		.and()
		.header("Authorization","")
		.and()
		.contentType("")
		.log().all()
		.when()
		.post("/master")
		.then()
		.log().all()
		.statusCode(401);
	}
	
}
