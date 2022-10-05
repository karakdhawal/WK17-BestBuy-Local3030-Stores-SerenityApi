package com.localhost3030.storeinfo;

import com.localhost3030.testbase.TestBase;
import com.localhost3030.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class StoreCRUDTestWithSteps extends TestBase {

    static String name = "Minnetonka " + TestUtils.getRandomValue();
    static String type = "BigBox " + TestUtils.getRandomValue();
    static String address = TestUtils.getRandomValue() + " Ridgedale Dr";
    static String address2 = "";
    static String city = "Hopkins " + TestUtils.getRandomValue();
    static String state = "MN";
    static String zip = TestUtils.getRandomValue();

    static int storeId;

    @Steps
    StoreSteps storeSteps;

    @Title("This will get list of Stores")
    @Test
    public void test001 (){
        storeSteps.getAllStoresInfo().log().all().statusCode(200);
    }

    @Title("This will create a store")
    @Test
    public void test002 (){
        storeSteps.createStore(name, type, address, address2, city, state, zip).log().all().statusCode(201);
    }

    @Title("This will verify store is created")
    @Test
    public void test003 (){
        HashMap<String, Object> storeMap = storeSteps.getStoreInfoByName(name);
        Assert.assertThat(storeMap, hasValue(name));
        storeId = (int) storeMap.get("id");
        System.out.println(storeId);
    }
    @Title("update the store and Verify the updated info")
    @Test
    public void test004 (){
        name = name+"any"; // tried any as its inside ""
        storeSteps.updateStore(storeId, name, type,address, address2,city,state,zip);
        HashMap<String, Object> storeMap = storeSteps.getStoreInfoByName(name);
        Assert.assertThat(storeMap, hasValue(name));
    }
    @Title("Delete the store and verify if the store is deleted")
    @Test
    public void test005 (){
        storeSteps.deleteStoreInfoById(storeId).log().all().statusCode(200);
        storeSteps.getStoreInfoByStoreId(storeId).log().all().statusCode(404);
    }



}
