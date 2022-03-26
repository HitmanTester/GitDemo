package stepDefinitions;

import io.cucumber.java.en.And;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import io.cucumber.java.en.*;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import reqeustBuilder.TokenReuestBuilder;
import static org.junit.Assert.*;

public class TokenApi {
	String strReqdata;
	Response resp;
	
	@Given("User creates request data with {string} and {string}")
	public void user_creates_request_data_with_and(String username, String password) throws JsonProcessingException {
		TokenReuestBuilder reqdata= new TokenReuestBuilder();
		reqdata.setUserNameOrEmailAddress(username);
		reqdata.setPassword(password);
		
		//convert object to string
		ObjectMapper objectmapper = new ObjectMapper();
	   strReqdata =	objectmapper.writerWithDefaultPrettyPrinter().writeValueAsString(reqdata);
	
	System.out.println(strReqdata);
	}
	
	@Given("User submits request to token API")
	public void user_submits_request_to_token_api() {
	   
		 resp = given().contentType(ContentType.JSON).
			body(strReqdata).post("http://dev-mb.yoll.io/api/tokenauth/authenticate");	
		
		resp.prettyPeek();
	}
	@Given("User validates if status code is {int}")
	public void user_validates_if_status_code_is(Integer expectedStatusCode) {
	   
	int actualStatusCode = 	resp.getStatusCode();
	
	assertEquals(expectedStatusCode, actualStatusCode);
		System.out.println("Actual status code is "+actualStatusCode);
	}
	
	@Then("User retrieves access token from response")
	public void user_retrieves_access_token_from_response() {
	 
	String token =	JsonPath.read(resp.asString(), "$.result.accessToken");
	System.out.println(token);
	
	assertNotNull(token);
	}



	

}
