package TestScript;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;
public class CreateBugInJira {


	public static void main(String[] args) {
		
		RestAssured.baseURI="https://ganeshnaik1210.atlassian.net/";
		
		String CreateIssueResponse = given()
		.header("Content-Type","application/json")
		.header("Authorization","Basic Z2FuZXNobmFpMTIxQGdtYWlsLmNvbTpBVEFUVDN4RmZHRjBScFFpbFNYRmtzTmFTZUxhcVFzVVhzMmJITWI0Rm40YXpXYWxEZUtYakpORm9vNE4yeHp0Q2dKV0RpVkZtTlNVQ09NSkxkanlHMUNCOEJnU2V3bDgwSTU2eXNaSXNueU8yS1FhSmFjWUtzazRFd3lSTFNjUXhBOU9MeElZb0J1ZldxNFQydHBwaG8xYzd2RDl2Um9wOXFmYktPekxBTUY5OTJrWlZQUk45alk9MzIyNzk1QjM=")
		.body("{\r\n"
				+ "    \"fields\": {\r\n"
				+ "       \"project\":\r\n"
				+ "       {\r\n"
				+ "          \"key\": \"SCRUM\"\r\n"
				+ "       },\r\n"
				+ "       \"summary\": \"Creating Jira Bug from API automation\",\r\n"
				+ "       \"issuetype\": {\r\n"
				+ "          \"name\": \"Bug\"\r\n"
				+ "       }\r\n"
				+ "   }\r\n"
				+ "}\r\n"
				+ "").log().all()
		
		.when().post("rest/api/3/issue")
		
		.then().log().all().assertThat().statusCode(201).extract().response().asString();
		
		
		JsonPath jsn=new JsonPath(CreateIssueResponse);
		String IssueId=jsn.get("id");
		
		System.out.println("Issue Id: "+IssueId);
		
		//*************************************Add an Attachments*************************************
		
		String ResponseForAttach = given()
		.pathParam("Key", IssueId)
		.header("X-Atlassian-Token","no-check")
		.header("Authorization","Basic Z2FuZXNobmFpMTIxQGdtYWlsLmNvbTpBVEFUVDN4RmZHRjBScFFpbFNYRmtzTmFTZUxhcVFzVVhzMmJITWI0Rm40YXpXYWxEZUtYakpORm9vNE4yeHp0Q2dKV0RpVkZtTlNVQ09NSkxkanlHMUNCOEJnU2V3bDgwSTU2eXNaSXNueU8yS1FhSmFjWUtzazRFd3lSTFNjUXhBOU9MeElZb0J1ZldxNFQydHBwaG8xYzd2RDl2Um9wOXFmYktPekxBTUY5OTJrWlZQUk45alk9MzIyNzk1QjM=")
		.multiPart("file", new File("D:/GaneshNaik/Udemi/imageOfDeskTop.png"))
		
		.when()
		.post("rest/api/3/issue/{Key}/attachments")
		
		.then()
		.assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js=new JsonPath(ResponseForAttach);
		String filename=js.getString("filename");
		System.out.println("filename: "+filename);
		
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
}
