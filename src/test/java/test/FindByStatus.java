package test;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.restassured.response.Response;
import utils.PetAvailablePojo;
import utils.ReadFile;

public class FindByStatus extends BaseTest{
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
	public void availableStatusTest() {		
		request.param("status", "available");
		response = request.get("/pet/findByStatus");
//		System.out.println(response.getBody().asPrettyString());
	}
	
	@Test(dependsOnMethods = {"availableStatusTest"})
	public void validateSatusCode() {
		int actualResult = response.getStatusCode();
		Reporter.log("Expected Result  = " + RESPONSE_CODE_200);
		Reporter.log("Actual Result = " + actualResult);
		assertTrue(actualResult == RESPONSE_CODE_200, "Mimstach in the response code");
	}
	
	@Test(dependsOnMethods = {"availableStatusTest"})
	public void validateSatusMessage() {
		String actualResult = response.getStatusLine();
		Reporter.log("Expected Result  = " + RESPONSE_LINE_200);
		Reporter.log("Actual Result = " + actualResult);
		assertTrue(actualResult.equals(RESPONSE_LINE_200), "Mismatch in the respose line");
	}
	
	@Test(dependsOnMethods = {"availableStatusTest"})
	public void validateContentType() {
		String actualResult = response.contentType();
		Reporter.log("Expected Result  = " + CONTENT_TYPE);
		Reporter.log("Actual Result = " + actualResult);
		assertTrue(actualResult.equals(CONTENT_TYPE), "Mismatch in the content type");
	}

	@Test(dependsOnMethods = {"availableStatusTest"})
	public void validateResponseBody() {
		String responseFile = "test.yaml";
		List<PetAvailablePojo> expectedResult = rf.readYamlAsList(responseFile);
		List<PetAvailablePojo> actualResult = response.getBody().as(List.class);
		Reporter.log("Expected Result = " + expectedResult);
		Reporter.log("Actual Result = " + actualResult);
		assertTrue(actualResult.equals(expectedResult), "Mismatch in the response body");
		
//		ArrayList<String> test = new ArrayList<String>();
//		test.add(response.getBody().jsonPath().getString("id").toString());
//		System.out.println(test.get(0).contains("9223372016900012413"));
	}
	
	
}
