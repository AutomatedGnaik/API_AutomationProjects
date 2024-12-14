package TestScript;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import InputData.PayLoad;

import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DynamicJSON {

	
	@Test(dataProvider="BookData")
	public void addBook(String isbn, String aisle) {
		RestAssured.baseURI="http://216.10.245.166";
		
		
	String Response=given()
		.header("Conten-Type","application/json")
		.body(PayLoad.AddBook(isbn,aisle)).
		
		when().post("Library/Addbook.php").
		
		then().assertThat().statusCode(200).extract().response().asString();
		
		
	JsonPath js= new JsonPath(Response);
	String ID=js.get("ID");
	System.out.println("ID :"+ ID);
	
	
	}
	
	
	@DataProvider(name="BookData")
	public Object[][] getData() {
		//add book details using multi dimensional arrary
		
	return 	new Object[][] {
			{"Ganesh","1210"},{"Praveen","1120"},{"balu","12101"}
		};
		
	}
	
	
}
