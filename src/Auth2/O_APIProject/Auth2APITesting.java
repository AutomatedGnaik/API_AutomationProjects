package Auth2.O_APIProject;
import static io.restassured.RestAssured.given;

import io.restassured.path.json.JsonPath;

public class Auth2APITesting {

	
	public static void main(String [] args ) {
		
		String url ="https://rahulshettyacademy.com/getCourse.php?state=verifyfjdss&code=4%2FvAHBQUZU6o4WJ719NrGBzSELBFVBI9XbxvOtYpmYpeV47bFVExkaxWaF_XR14PHtTZf7ILSEeamywJKwo_BYs9M&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&session_state=0c32992f0d47e93d273922018ade42d1072b9d1f..a35c&prompt=none#";

                     
		String partialcode=url.split("code=")[1];

		String code=partialcode.split("&scope")[0];


		System.out.println(code);
		
		System.out.println("********************* GetAuthorization Code ******************");
		String AccessTokenResponse = given().urlEncodingEnabled(false)
		.queryParam("code", code)
		.queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.queryParam("grant_type", "authorization_code")
		.queryParams("state", "verifyfjdss")
		.queryParams("session_state", "ff4a89d1f7011eb34eef8cf02ce4353316d9744b..7eb8")
		.queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
		.when().log().all()
		.post("https://www.googleapis.com/oauth2/v4/token").asString();
		
		JsonPath js= new JsonPath(AccessTokenResponse);
		String AccessToken=js.getString("access_token");
		
		
		
		System.out.println("*********** GetAccessToken ***********");
		String Response = given().queryParam("access_token ", AccessToken)
		.when().log().all()
		.get("https://rahulshettyacademy.com/getCourse.php").asString();
		System.out.println(Response);
		
		
		//******************************** Changes *********************
		
		System.out.println("Ganesh Has changed somthing");

		System.out.println("hegadru madi conflicts barbeku");

		
		System.out.println("Ganesh Has changed somthing");
		System.out.println("kiran Has changed somthing");
		System.out.println("Ganesh Has changed somthing");
		System.out.println("kiran Has changed somthing");
		
		
		
		
		
	}

	
}
