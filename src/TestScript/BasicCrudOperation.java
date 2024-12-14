package TestScript;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

import InputData.PayLoad;
public class BasicCrudOperation {

	
	public static void main(String[] args) throws IOException {
		//Given : Here we will give all API input details
		//When  : Submit the API(here we will mension wchich method we are going to use like POST,GET etc... )
		//Then  : Here validate the API Response
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
		System.out.println("========================================== POST Method Operation ==============================================");
		

		String Response=given()		
		.log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(new String(Files.readAllBytes(Paths.get("D:\\GaneshNaik\\Udemi"))))     // here I have given the json files as body
		
		.when()
		.post("maps/api/place/add/json")
		
		.then()
		.log().all().assertThat().statusCode(200).body("scope",equalTo("APP")).extract().response().asString();
		System.out.println("-------------------------- Response --------------------------------");
		System.out.println(Response);
		
		JsonPath jsn=new JsonPath(Response);
		String palceId = jsn.getString("place_id");
		System.out.println("palceId : "+palceId);
		
		
	    System.out.println("========================================== PUT Method Operation ==============================================");
	   
	    
		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json").body("{\r\n"
				+ "\"place_id\":\""+palceId+"\",\r\n"
				+ "\"address\":\""+PayLoad.NewAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}")
		
		.when()
		.put("maps/api/place/update/json")
		
		.then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		
		System.out.println("========================================== GET Method Operation ==============================================");
		
		String Getresponse = given()
		.log().all().queryParam("key", "qaclick123").queryParam("place_id", palceId)
		
		.when()
		.get("maps/api/place/get/json")
		
		.then()
		.log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath jsn1=new JsonPath(Getresponse);
		String UpdatedAddress = jsn1.getString("address");
		System.out.println("UpdatedAddress : "+UpdatedAddress);
		Assert.assertEquals(UpdatedAddress, PayLoad.NewAddress);
		
		System.out.println("========================================== DELETE Method Operation ==============================================");
		
		given()
		.log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body("{\r\n"
				+ "    \"place_id\":\""+palceId+"\"\r\n"
				+ "}\r\n"
				+ "")
		
		.when()
		.delete("maps/api/place/delete/json")
		
		.then().log().all().assertThat().statusCode(200).body("status", equalTo("OK"));
		
		
       System.out.println("========================================== Again GET Method Operation After Delete ==============================================");
		
		 given()
		.log().all().queryParam("key", "qaclick123").queryParam("place_id", palceId)
		
		.when()
		.get("maps/api/place/get/json")
		
		.then()
		.log().all().assertThat().statusCode(404).body("msg", equalTo("Get operation failed, looks like place_id  doesn't exists"));
		
		
	}

	
}
