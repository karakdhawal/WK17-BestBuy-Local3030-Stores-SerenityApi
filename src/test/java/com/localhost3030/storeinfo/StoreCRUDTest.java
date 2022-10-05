package com.localhost3030.storeinfo;

import com.localhost3030.constants.EndPoints;
import com.localhost3030.model.StorePojo;
import com.localhost3030.testbase.TestBase;
import com.localhost3030.utils.TestUtils;
import io.restassured.http.ContentType;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static net.serenitybdd.rest.RestRequests.given;
import static org.hamcrest.Matchers.hasValue;


@RunWith(SerenityRunner.class)
public class StoreCRUDTest extends TestBase {

    static String name = "Minnetonka " + TestUtils.getRandomValue();
    static String type = "BigBox " + TestUtils.getRandomValue();
    static String address = TestUtils.getRandomValue() + " Ridgedale Dr";
    static String address2 = "";
    static String city = "Hopkins " + TestUtils.getRandomValue();
    static String state = "MN";
    static String zip = TestUtils.getRandomValue();

    static int storeId;


    @Title("This will get list of stores")
    @Test
    public void test001 (){
        SerenityRest.given()
                .when()
                .get(EndPoints.GET_ALL_STORES)
                .then()
                .log().all()
                .statusCode(200);
    }

    @Title("This will create a store")
    @Test
    public void test002 (){
        StorePojo storePojo = new StorePojo();
        storePojo.setName(name);
        storePojo.setType(type);
        storePojo.setAddress(address);
        storePojo.setAddress2(address2);
        storePojo.setCity(city);
        storePojo.setState(state);
        storePojo.setZip(zip);
    SerenityRest.given().log().all()
            .contentType(ContentType.JSON)
            .body(storePojo)
            .when()
            .post()
            .then().log().all().statusCode(201);

    }


    @Title("This will verify store is created")
    @Test
    public void test003 (){
        String p1 = "data.findAll{it.name = '";
        String p2 = "'}.get(0)";
        HashMap<String, Object> storeMap = SerenityRest.given().log().all()
                .when()
                .get(EndPoints.GET_ALL_STORES)
                .then()
                .statusCode(200)
                .extract()
                .path(p1+name+p2);
        Assert.assertThat(storeMap, hasValue(name));
        storeId = (int) storeMap.get("id");
    }

    @Title("Update the store and Verify the updated info")
    @Test
    public void test004 () {
        name = name+"any"; // tried any as its inside ""

        StorePojo storePojo = new StorePojo();
        storePojo.setName(name);
        storePojo.setType(type);
        storePojo.setAddress(address);
        storePojo.setAddress2(address2);
        storePojo.setCity(city);
        storePojo.setState(state);
        storePojo.setZip(zip);
        SerenityRest.given().log().all()
                .header("Content-Type", "application/json; charset=UTF-8")
                .pathParam("storeId", storeId)
                .body(storePojo)
                .when()
                .put(EndPoints.UPDATE_STORE_BY_ID)
                .then().log().all().statusCode(200);

        String p1 = "data.findAll{it.name='";
        String p2 = "'}.get(0)";
        HashMap<String, Object> storeMap = SerenityRest.given().log().all()
                .when()
                .get(EndPoints.GET_ALL_STORES)
                .then()
                .statusCode(200)
                .extract()
                .path(p1+name+p2);
        Assert.assertThat(storeMap, hasValue(name));

    }

    @Title("Delete the store and verify if the product is deleted")
    @Test
    public void test005 (){
        SerenityRest.given()
                .pathParam("storeId", storeId)
                .when()
                .delete(EndPoints.DELETE_STORE_BY_ID)
                .then()
                .statusCode(200);
        SerenityRest.given()
                .pathParam("storeId", storeId)
                .when()
                .delete(EndPoints.DELETE_STORE_BY_ID)
                .then()
                .statusCode(404);
    }

}
