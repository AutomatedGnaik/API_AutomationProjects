package Deserialization;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import io.restassured.path.json.JsonPath;

public class OauthAPItestingUsingPOJOclasses {

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

		getCourses getCourses = given().log().all()
		.queryParam("access_token",AccessToken)
        
        .when()
        .log().all().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
        
		  .then().extract().response().as(getCourses.class);

		String getInstructor = getCourses.getInstructor();
		String getUrl = getCourses.getUrl();
		String getServices = getCourses.getServices();
		String getLinkedIn = getCourses.getLinkedIn();

		
		System.out.println("*************************Print Information about courses *************************");
		System.out.println("Instructor : "+getInstructor);
		System.out.println("Url : "+getUrl);
		System.out.println("Services : "+getServices);
		System.out.println("LinkedIn : "+getLinkedIn);

		System.out.println("*************************Print all courseTitles from webautomaton*************************");
		String [] courseTitle= {"Selenium Webdriver Java","Cypress","Protractor"};
		
		ArrayList<String> Ar=new ArrayList<String>();
		List<WebAutomation> getAllcoursesWebAutomation = getCourses.getCourses().getWebAutomation();
		int count = getAllcoursesWebAutomation.size();
	    
	    for (int i = 0; i < count; i++) {
	    	
	    	// add all course titles to array list
	    	Ar.add(getAllcoursesWebAutomation.get(i).getCourseTitle());
			System.out.println("CourseTitle : "+getAllcoursesWebAutomation.get(i).getCourseTitle());
			
			
			int getPrice = getAllcoursesWebAutomation.get(i).getPrice();
			System.out.println("Price : "+getPrice);
		}
	    
	    List<String> ExpectedCourseTitles = Arrays.asList(courseTitle);
	    
	    
	    Assert.assertTrue(Ar.equals(ExpectedCourseTitles));
	    
	    
	    
	    System.out.println("*************************Print API SoapUI Webservices testing price*************************");
	    
	    List<API> getAllapi=getCourses.getCourses().getAPI();
	    int countAllapi = getAllapi.size();
	    for (int i = 0; i < countAllapi; i++) {
			
	    	if(getAllapi.get(i).getCourseTitle().contentEquals("SoapUI Webservices testing")) {
	    		
	    		System.out.println("Price: "+getAllapi.get(i).getPrice());
	    	}
	    
		}
	    
	    
	    
	    
	}

}
