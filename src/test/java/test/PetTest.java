package test;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.restassured.response.Response;
import utils.ReadFile;

public class PetTest extends BaseTest {
	Response response;
	ReadFile rf;
	String requestFile;
	String responseFile;
	Gson gson;
	
	
	@BeforeClass
	public void beforeClass() {
		rf = new ReadFile();
		gson = new GsonBuilder().create();
	}
	
	@Test
	public void validPetTest() {
		requestFile = "petRequest.yaml";
		Map<String, Object> reqBody = rf.readYaml(requestFile);
		request.body(reqBody);
		response = request.post("/pet");
	}
	
	@Test(dependsOnMethods = {"validPetTest"})
	public void validateSatusCode() {
		int actualResult = response.getStatusCode();
		Reporter.log("Expected Result  = " + RESPONSE_CODE_200);
		Reporter.log("Actual Result = " + actualResult);
		assertTrue(actualResult == RESPONSE_CODE_200, "Mimstach in the response code");
	}
	
	@Test(dependsOnMethods = {"validPetTest"})
	public void validateSatusMessage() {
		String actualResult = response.getStatusLine();
		Reporter.log("Expected Result  = " + RESPONSE_LINE_200);
		Reporter.log("Actual Result = " + actualResult);
		assertTrue(actualResult.equals(RESPONSE_LINE_200), "Mismatch in the respose line");
	}
	
	@Test(dependsOnMethods = {"validPetTest"})
	public void validateContentType() {
		String actualResult = response.contentType();
		Reporter.log("Expected Result  = " + CONTENT_TYPE);
		Reporter.log("Actual Result = " + actualResult);
		assertTrue(actualResult.equals(CONTENT_TYPE), "Mismatch in the content type");
	}
	
	@Test(dependsOnMethods = {"validPetTest"})
	public void validateResponseBody() {
		String responseFile = "petResponse.yaml";
		String expectedResult = gson.toJson(rf.readYaml(responseFile));
		String actualResult = response.body().asString();
		
		System.out.println(response.body().jsonPath().getString("category.name"));

		Reporter.log("Expected Result = " + expectedResult.toString());
		Reporter.log("Actual Result = " + response.body().asString());
		assertTrue(actualResult.equals(expectedResult), "Mismatch in the content type");
	}
}