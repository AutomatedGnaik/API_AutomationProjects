package TestScript;

import static io.restassured.RestAssured.*;

import io.restassured.path.json.JsonPath;

public class OauthAPItesting {

	public static void main(String[] args) {
		
		
		System.out.println("*********************************** Post Operation to Get Access Token **********************************");
		String Response=given()
		.formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.formParam("grant_type", "client_credentials")
		.formParam("scope", "trust")
		
	   .when()
	    .log().all().post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token")
		
	    .then()
	    .log().all().assertThat().statusCode(200).extract().response().asString();
	    
	    JsonPath json=new JsonPath(Response);
	    String AccessToken = json.get("access_token");
	    System.out.println("AccessToken : "+AccessToken);
	    
		System.out.println("*********************************** GET Operation to Get Personal Information **********************************");

	    String Response2 = given()
        .queryParam("access_token",AccessToken)
        
        .when()
        .log().all().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
        
		  .then().extract().response().asString();
		  System.out.println(Response2);
	    
        
	}

}
