package testCases;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class TC_VideoGameAPI {

	@Test(priority=1)
	public void test_getAllVideoGames() {

		RestAssured.baseURI = "http://localhost:8080/app/videogames";
		int statusCode = RestAssured.given().accept(ContentType.JSON).get("").statusCode();
		Assert.assertEquals(statusCode, 200);

	}

	@Test(priority=2)
	public void test_addNewVideoGame() {

		HashMap<String, String> requestData = new HashMap<String, String>();
		requestData.put("id", "11");
		requestData.put("name", "Spider-Man");
		requestData.put("releaseDate", "2023-08-20T09:38:14.404Z");
		requestData.put("reviewScore", "5");
		requestData.put("category", "Adventure");
		requestData.put("rating", "Universal");

		Response response = 
				RestAssured.given().contentType(ContentType.JSON).body(requestData)
				.post("http://localhost:8080/app/videogames")
				.then().statusCode(200).log().body().extract().response();

		String stringResponse = response.asString();
		Assert.assertEquals(stringResponse.contains("Record Added Successfully"), true);

	}

	@Test(priority=3)
	public void test_getVideoGame() {

		RestAssured.given()
		.accept(ContentType.JSON)
		.when()
		.get("http://localhost:8080/app/videogames/11")
		.then()
		.statusCode(200)
		.log().body()
		.body("id", equalTo(11))
		.body("name", equalTo("Spider-Man"))
		.body("releaseDate", equalTo("2023-08-20"))
		.body("reviewScore", equalTo(5))
		.body("category", equalTo("Adventure"))
		.body("rating", equalTo("Universal")); 

	}

	@Test(priority=4)
	public void test_updateVideoGame() {

		HashMap<String, String> requestData = new HashMap<String, String>();
		requestData.put("id", "2");
		requestData.put("name", "test");
		requestData.put("releaseDate", "2023-08-21T11:11:02.595Z");
		requestData.put("reviewScore", "0");
		requestData.put("category", "test");
		requestData.put("rating", "test");

		RestAssured.baseURI = "http://localhost:8080/app/videogames/";		
		RestAssured.given()
		.accept(ContentType.JSON)
		.contentType(ContentType.JSON)
		.body(requestData)
		.when()
		.put("http://localhost:8080/app/videogames/2")
		.then()
		.statusCode(200)
		.log().body()
		.body("id", equalTo(2))
		.body("name", equalTo("test"))
		.body("releaseDate", equalTo("2023-08-21"))
		.body("reviewScore", equalTo(0))
		.body("category", equalTo("test"))
		.body("rating", equalTo("test")); 
	}

	@Test(priority=5)
	public void test_deleteVideoGame() {

		Response response = 
				RestAssured.given()
				.when()
				.delete("http://localhost:8080/app/videogames/11")
				.then()
				.statusCode(200)
				.log().body()
				.extract().response();

		String stringResponse = response.asString();
		Assert.assertEquals(stringResponse.contains("Record Deleted Successfully"), true);

	}


}