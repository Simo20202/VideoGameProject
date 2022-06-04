package testCases;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;

import org.hamcrest.Matchers;


public class TC_VideoGameAPI {
	
	@Test(priority=1)
	public void test_getAllVideoGames()
	{
		given()
		
		.when()
			.get("http://localhost:8080/app/videogames")
			
		.then()
			.statusCode(200);
}
	@Test(priority=2)
	public void test_addNewVideoGame()
	{
		HashMap data = new HashMap();
		data.put("id","101");
		data.put("name","Spider-Man");
		data.put("releaseDate", "2022-06-03T12:52:42.277Z");
		data.put("reviewScore","5");
		data.put("category","adventure");
		data.put("rating","Universal");
		
		Response res=
			given()
				.contentType("application/json")
				.body(data)
				
			.when()
				.post("http://localhost:8080/app/videogames")
			
			.then()
				.statusCode(200)
				.log().body()
				.extract().response();
		
		String jsonString=res.asString();
		Assert.assertEquals(jsonString.contains("Record Added Successfully"),true);
	
	}
	
	@Test(priority=3)
	public void test_getVideoGame() 
	{
		given()
		.when()
			.get("http://localhost:8080/app/videogames/101")
			
		.then()
			.statusCode(200)
			.log().body()
			.body("videoGame.id",equalTo("101"))
			.body("videoGame.name",equalTo("Spider-Man"));
			
	}
	
	@Test(priority=4)
	public void test_updateVideoGame()
	{
		HashMap data = new HashMap();
		data.put("id","101");
		data.put("name","Pacman");
		data.put("releaseDate", "2022-06-03T12:57:42.277Z");
		data.put("reviewScore","4");
		data.put("category","adventure");
		data.put("rating","Universal");
		
		given()
			.contentType("application/json")
			.body(data)
			
		.when()
			.put("http://localhost:8080/app/videogames/101")
			
		.then()
			.statusCode(200)
			.log().body()
			.body("videoGame.id",equalTo("101"))
			.body("videoGame.name",equalTo("Pacman"));
	}
	
	@Test(priority=5)
	public void test_deleteVideoGame() throws InterruptedException
	{
		Response res=
		given()
		
		.when()
			.delete("http://localhost:8080/app/videogames/101")
			
		.then()
			.statusCode(200)
			.log().body()
			.extract().response();
		
		Thread.sleep(3000);
		String jsonString=res.asString();
		Assert.assertEquals(jsonString.contains("Record Deleted Successfully"),true);
			
	}
	
	
}
