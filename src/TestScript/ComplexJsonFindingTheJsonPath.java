package TestScript;

import org.testng.Assert;

import InputData.PayLoad;
import io.restassured.path.json.JsonPath;

public class ComplexJsonFindingTheJsonPath {

	
	public static void main(String[] args) {
	
	JsonPath jsComplex=new JsonPath(PayLoad.ComplexJsonresponse());
	
	System.out.println("******* Print No of courses returned by API ******");	
	int Conunt=jsComplex.getInt("courses.size()");
	System.out.println(Conunt);
	
	System.out.println("******* Print Purchase Amount ******");	
	int PurchaseAmount=jsComplex.getInt("dashboard.purchaseAmount");
	System.out.println(PurchaseAmount);
	
	System.out.println("******* Print Title of the first course ******");	
	String Title=jsComplex.getString("courses[0].title");
	System.out.println(Title);
	
	System.out.println("******* Print All course titles and their respective Prices ******");
	for (int i = 0; i < Conunt; i++) {
		String Titles=jsComplex.getString("courses["+i+"].title");
		int Price=jsComplex.get("courses["+i+"].price");
	System.out.print(Titles+" :-");
	System.out.println(Price+"rs");
	}
	
	System.out.println("******* Print no of copies sold by RPA Course ******");
	for (int i = 0; i < Conunt; i++) {
		String Titles=jsComplex.getString("courses["+i+"].title");
	     if (Titles.contentEquals("RPA")) {
			int copies=jsComplex.get("courses["+i+"].copies");
			System.out.println("No of copies :"+copies);
			break;
		}
	}
    

    
	System.out.println("******* Verify if Sum of all Course prices matches with Purchase Amount ******"); 
    int sum = 0;
    for (int i = 0; i < Conunt; i++) {
    	int Price=jsComplex.get("courses["+i+"].price");
    	int Copies=jsComplex.get("courses["+i+"].copies");
    	int total=Price*Copies;
    	sum=sum+total;
    	
	}
	
    Assert.assertEquals(sum, PurchaseAmount);
    System.out.println(sum);
    System.out.println(PurchaseAmount);
    
    
    
    
    
    
    
    
    
    
    
    
	}
	
}
