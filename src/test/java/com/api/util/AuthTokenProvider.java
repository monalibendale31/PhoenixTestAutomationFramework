package com.api.util;

import org.hamcrest.Matchers;

import com.api.constant.Roles;
import com.api.pojo.UserCredentials;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class AuthTokenProvider {

	private AuthTokenProvider() {

	}

	public static String getToken(Roles role) {

		UserCredentials usercred = null;

		if (role==Roles.FD) {
			usercred = new UserCredentials("iamfd", "password");
		} else if (role==Roles.SUP) {
			usercred = new UserCredentials("iamsup", "password");
		} else if (role==Roles.ENG) {
			usercred = new UserCredentials("iameng", "password");
		} else if (role==Roles.QC) {
			usercred = new UserCredentials("iamqc", "password");
		}

		String token = RestAssured.given().baseUri(ConfigManager.readProperties("BASE_URI"))
				.contentType(ContentType.JSON).accept(ContentType.ANY).body(usercred).when().post("login").then().log()
				.ifValidationFails().statusCode(200).body("message", Matchers.equalTo("Success")).extract().body()
				.jsonPath().getString("data.token");
		System.out.println(token);
		return token;
	}
}
