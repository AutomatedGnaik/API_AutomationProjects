package EndToEndAPITestingForProductOrder;
import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;

public class EndToEndTestingForProductOrder {

	public static void main(String [] args) {


		System.out.println("************************************************** Login Operation **************************************************");
		LoginBodyPOJO LG=new LoginBodyPOJO();
		LG.setUserEmail("ganeshnaik638@gmail.com");
		LG.setUserPassword("Kiran@1210");

		RequestSpecification Req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/").setContentType(ContentType.JSON).build();

		RequestSpecification Request = given().spec(Req).log().all().body(LG);

		LoginResponsePOJO Response = Request.when().post("api/ecom/auth/login").

				then().log().all().statusCode(200).extract().response().as(LoginResponsePOJO.class);

		String Token = Response.getToken();
		String UserId = Response.getUserId();

		System.out.println("************************************************** Create Product Operation **************************************************");

		RequestSpecification CreateProductReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization", Token).build();

		RequestSpecification CreateproductRequest = given().spec(CreateProductReq).param("productName", "qwerty").param("productAddedBy", UserId).param("productCategory", "fashion")
				.param("productSubCategory","shirts").param("productPrice", "11500").param("productDescription", "Addias Originals").param("productFor", "women")
				.multiPart("productImage", new File("D://GaneshNaik//Udemi//Qwerty.png"));


		String CreateProductResponse = CreateproductRequest.when().post("/api/ecom/product/add-product")


				.then().log().all().assertThat().statusCode(201).extract().response().asString();


		JsonPath json=new JsonPath(CreateProductResponse);
		String productId = json.get("productId");

		System.out.println("************************************************** Create Order Operation **************************************************");

		OrdersDetailsPOJOclassForCreateOrder OrderDetails=new OrdersDetailsPOJOclassForCreateOrder();
		OrderDetails.setCountry("India");
		OrderDetails.setProductOrderedId(productId);

		List<OrdersDetailsPOJOclassForCreateOrder> AR=new ArrayList<OrdersDetailsPOJOclassForCreateOrder>();
		AR.add(OrderDetails);

		OrdersPOJOclassForCreateOrder Orders=new OrdersPOJOclassForCreateOrder();
		Orders.setOrders(AR);



		RequestSpecification CreateOrderReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON).addHeader("Authorization",Token).build();

		RequestSpecification CreateOrderRequest = given().spec(CreateOrderReq).log().all().body(Orders);


		String CreateOrderResponse = CreateOrderRequest.when().post("/api/ecom/order/create-order")

				.then().log().all().assertThat().statusCode(201).extract().response().asString();


		JsonPath json1=new JsonPath(CreateOrderResponse);
		String Message = json1.get("message");
		String ID=json1.getString("orders[0]");

		Assert.assertEquals(Message, "Order Placed Successfully");

		System.out.println("************************************************** View Order Operation **************************************************");

		RequestSpecification ViewOrderReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization",Token).build();	

		RequestSpecification ViewOrderRequest = given().spec(ViewOrderReq).queryParam("id", ID);

		String ViewOrderresponse = ViewOrderRequest.when().get("/api/ecom/order/get-orders-details")

				.then().log().all().assertThat().statusCode(200).extract().response().asString();

		JsonPath jsn=new JsonPath(ViewOrderresponse);
		String GetMessage = jsn.get("message");
		Assert.assertEquals(GetMessage, "Orders fetched for customer Successfully");
		String ActualID=jsn.get("data._id"); 
		Assert.assertEquals(ActualID,ID);


		System.out.println("************************************************** Delete Product Operation **************************************************");

		RequestSpecification DeleteProductReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization",Token).build();	

		RequestSpecification DeleteProductRequest = given().spec(DeleteProductReq).pathParam("productId",productId);

		String DeleteProductResponse=DeleteProductRequest.when().delete("/api/ecom/product/delete-product/{productId}")

				.then().log().all().assertThat().statusCode(200).extract().response().asString();

		JsonPath jsn2=new JsonPath(DeleteProductResponse);
		String DeleteMessage = jsn2.get("message");
		Assert.assertEquals(DeleteMessage, "Product Deleted Successfully");


		System.out.println("************************************************** Delete Order Operation **************************************************");

		RequestSpecification DeleteOrderReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization",Token).build();

		RequestSpecification DeleteOrderRequest = given().spec(DeleteProductReq).pathParam("orderId",ID);

		
		String DeleteOrderResponse=DeleteOrderRequest.when().delete("/api/ecom/order/delete-order/{orderId}")
		
				.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath jsn3=new JsonPath(DeleteOrderResponse);
		String DltOrderMsg = jsn3.get("message");
		Assert.assertEquals(DltOrderMsg, "Orders Deleted Successfully");
		
		
		
		
		
		


	}
}
