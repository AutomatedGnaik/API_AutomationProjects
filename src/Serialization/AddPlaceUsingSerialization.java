package Serialization;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import io.restassured.RestAssured;

public class AddPlaceUsingSerialization {

	

	public static void main(String[] args) throws IOException {
		//Given : Here we will give all API input details
		//When  : Submit the API(here we will mension wchich method we are going to use like POST,GET etc... )
		//Then  : Here validate the API Response
		
		System.out.println("========================================== POST Method Operation ==============================================");
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
		AddPlace AP=new AddPlace();
		
		AP.setAccuracy(50);
        AP.setName("Frontline house");
        AP.setPhone_number("(+91) 983 893 3937");
        AP.setAddress("29, side layout, cohen 09");
        AP.setWebsite("http://google.com");
        AP.setLanguage("Kannada");
        
        
        //Create a List and Provide data 
        List<String> list = new ArrayList<String>();
        list.add("shoe park");
        list.add("shop");
        AP.setTypes(list);
        
        // create a location class Object  and provide data 
        Location lcn=new Location();
        lcn.setLat(-38.383494);
        lcn.setLng(33.427362);
        AP.setLocation(lcn);
		
		 String Response=given()		
		.log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(AP)     // here I have given the json files as body
		
		.when()
		.post("maps/api/place/add/json")
		
		.then()
		.log().all().assertThat().statusCode(200).extract().response().asString();
		System.out.println("-------------------------- Response --------------------------------");
		System.out.println(Response);
	
}
	
	
	
	
	
	
}
