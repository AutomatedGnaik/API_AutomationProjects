package Serialization;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecBiulder {

	

	public static void main(String[] args) throws IOException {
		
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
		
        //********************** create RequestSpecBuilder Object  **************************  
        RequestSpecification Req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
        .addQueryParam("key", "qaclick123")
        .setContentType(ContentType.JSON).build();
        
        //********************** create ResponseSpecBuilder Object  **************************  
        ResponseSpecification Resp = new ResponseSpecBuilder()
        .expectStatusCode(200)
        .expectContentType(ContentType.JSON).build();
        
       
        //********************** Using SpecBuilder in to Test script **************************  
		 RequestSpecification Res = given().spec(Req).log().all()
		.body(AP);
		
		 String ResponseString=Res.when().post("maps/api/place/add/json")
				 
		.then().spec(Resp).log().all().extract().response().asString();
		
		 System.out.println(ResponseString);
	
}
	
	
	
	
	
	
}
