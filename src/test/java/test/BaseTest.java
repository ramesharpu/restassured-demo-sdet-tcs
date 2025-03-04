package test;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

@Test(groups = {"regression"})
public class BaseTest {
	
	String baseURL = "https://petstore.swagger.io/v2";
	static RequestSpecification request;
	final int RESPONSE_CODE_200 = 200;
	final String RESPONSE_LINE_200 = "HTTP/1.1 200 OK";
	final String CONTENT_TYPE = "application/json";	
	
	@BeforeSuite
	public void beforeSuite() {
		request = RestAssured.given().baseUri(baseURL).contentType("application/json");
	}
	
}
