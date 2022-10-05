package com.localhost3030.storeinfo;

import com.localhost3030.constants.EndPoints;
import com.localhost3030.model.StorePojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class StoreSteps {
    @Step ("getting all stores info: {0}")
    public ValidatableResponse getAllStoresInfo (){
        return SerenityRest.given()
                .when()
                .get(EndPoints.GET_ALL_STORES)
                .then();
    }

    @Step ("creating store with name:{0}, type:{1}, address:{2}, address2:{3}, city:{4}, state:{5}, zip:{6}")
    public ValidatableResponse createStore (String name, String type, String address, String address2, String city, String state, String zip){
        StorePojo storePojo = new StorePojo();
        storePojo.setName(name);
        storePojo.setType(type);
        storePojo.setAddress(address);
        storePojo.setAddress2(address2);
        storePojo.setCity(city);
        storePojo.setState(state);
        storePojo.setZip(zip);
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(storePojo)
                .when()
                .post()
                .then();
    }
    @Step ("getting store info by name: {0}")
    public HashMap<String, Object> getStoreInfoByName (String name){
        String p1 = "data.findAll{it.name = '";
        String p2 = "'}.get(0)";
        return SerenityRest.given().log().all()
                .when()
                .get(EndPoints.GET_ALL_STORES)
                .then()
                .statusCode(200)
                .extract()
                .path(p1+name+p2);
    }
    @Step ("update product info with storeId:{0}, name:{1}, type:{2}, address:{3}, address2:{4}, city:{5}, state:{6}, zip:{7}")
    public ValidatableResponse updateStore (int storeId, String name, String type, String address, String address2, String city, String state, String zip){
        StorePojo storePojo = new StorePojo();
        storePojo.setName(name);
        storePojo.setType(type);
        storePojo.setAddress(address);
        storePojo.setAddress2(address2);
        storePojo.setCity(city);
        storePojo.setState(state);
        storePojo.setZip(zip);
        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json; charset=UTF-8")
                .pathParam("storeId", storeId)
                .body(storePojo)
                .when()
                .put(EndPoints.UPDATE_STORE_BY_ID)
                .then();
    }
    @Step ("delete store info with storeId: {0}")
    public ValidatableResponse deleteStoreInfoById (int storeId){
        return SerenityRest.given()
                .pathParam("storeId", storeId)
                .when()
                .delete(EndPoints.DELETE_STORE_BY_ID)
                .then();
    }
    @Step ("getting store info by storeId: {0}")
    public ValidatableResponse getStoreInfoByStoreId (int storeId){
        return SerenityRest.given()
                .pathParam("storeId", storeId)
                .when()
                .delete(EndPoints.DELETE_STORE_BY_ID)
                .then();
    }

}
