package tests;

import com.google.gson.Gson;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import reqres_objects.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ReqresTests {
    public static final String BASE_URL = "https://reqres.in/api/";
    SoftAssert softAssert = new SoftAssert();

    @Test(description = "Create User, check status code, 'name' field, 'job' field")
    public void postCreateUserTest() {
        User user = User.builder()
                .name("morpheus")
                .job("leader")
                .build();
        given()
                .body(user)
                .header("Content-type", "application/json")
                .log().all()
                .when()
                .post(BASE_URL + "users")
                .then()
                .log().all()
                .body("name", equalTo("morpheus"),
                        "job", equalTo("leader"))
                .statusCode(201);
    }

    @Test(description = "Get list of users and check status code and all exists fields of the first user")
    public void getUsersListTest() {

        String body =
                given()
                        .log().all()
                        .when()
                        .get(BASE_URL + "users?page=2")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract().body().asString();

        UserList usersList = new Gson().fromJson(body, UserList.class);
        softAssert.assertEquals(usersList.getData().get(0).getId(), 7);
        softAssert.assertEquals(usersList.getData().get(0).getFirstName(), "Michael");
        softAssert.assertEquals(usersList.getData().get(0).getLastName(), "Lawson");
        softAssert.assertEquals(usersList.getData().get(0).getEmail(), "michael.lawson@reqres.in");
        softAssert.assertEquals(usersList.getData().get(0).getAvatar(), "https://reqres.in/img/faces/7-image.jpg");
    }

    @Test(description = "Get single user, check status code and all exists fields of the first user")
    public void getSingleUser() {
        String body =
                given()
                        .log().all()
                        .when()
                        .get(BASE_URL + "users/2")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract().body().asString();
        User user = new Gson().fromJson(body, User.class);
        softAssert.assertEquals(user.getData().getId(), 2);
        softAssert.assertEquals(user.getData().getFirstName(), "Janet");
        softAssert.assertEquals(user.getData().getLastName(), "Weaver");
        softAssert.assertEquals(user.getData().getEmail(), "janet.weaver@reqres.in");
        softAssert.assertEquals(user.getData().getAvatar(), "https://reqres.in/img/faces/2-image.jpg");
    }

    @Test(description = "Get single user not found, check status code")
    public void getSingleUserNotFoundTest(){
        given()
                .log().all()
                .when()
                .get(BASE_URL + "users/23")
                .then()
                .log().all()
                .statusCode(404);
    }

    @Test(description = "Get unknown list endpoint, check status code and all exists fields of the first user")
    public void getUnknownListTest(){
        String body =
                given()
                        .log().all()
                        .when()
                        .get(BASE_URL + "unknown")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract().body().asString();

        UnknownList unknownList = new Gson().fromJson(body, UnknownList.class);
        softAssert.assertEquals(unknownList.getData().get(0).getId(), 1);
        softAssert.assertEquals(unknownList.getData().get(0).getName(), "Michael");
        softAssert.assertEquals(unknownList.getData().get(0).getYear(), 2000);
        softAssert.assertEquals(unknownList.getData().get(0).getColor(), "#98B2D1");
        softAssert.assertEquals(unknownList.getData().get(0).getPantoneValue(), "15-4020");
    }

    @Test(description = "Get unknown endpoint, check status code and all exists fields of the  user")
    public void getUnknowntest(){
        String body =
                given()
                        .log().all()
                        .when()
                        .get(BASE_URL + "unknown/2")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract().body().asString();

        Unknown unknown = new Gson().fromJson(body, Unknown.class);
        softAssert.assertEquals(unknown.getId(), 2);
        softAssert.assertEquals(unknown.getName(), "Michael");
        softAssert.assertEquals(unknown.getYear(), 2000);
        softAssert.assertEquals(unknown.getColor(), "#98B2D1");
        softAssert.assertEquals(unknown.getPantoneValue(), "15-4020");
    }

    @Test(description = "Get unknown not found, check status code")
    public void getUnknownNotFoundTest(){
        given()
                .log().all()
                .when()
                .get(BASE_URL + "unknown/23")
                .then()
                .log().all()
                .statusCode(404);
    }

    @Test(description = "Update(put) User, check status code, 'name' field, 'job' field")
    public void putUpdateUserTest() {
        User user = User.builder()
                .name("Eugene")
                .job("zion resident")
                .build();
        given()
                .body(user)
                .header("Content-type", "application/json")
                .log().all()
                .when()
                .put(BASE_URL + "users/2")
                .then()
                .log().all()
                .body("name", equalTo("Eugene"),
                        "job", equalTo("zion resident"))
                .statusCode(200);
    }

    @Test(description = "Update(patch) User, check status code, 'name' field, 'job' field")
    public void patchUpdateUserTest() {
        User user = User.builder()
                .name("ChirikiChik")
                .job("zion resident")
                .build();
        given()
                .body(user)
                .header("Content-type", "application/json")
                .log().all()
                .when()
                .patch(BASE_URL + "users/2")
                .then()
                .log().all()
                .body("name", equalTo("ChirikiChik"),
                        "job", equalTo("zion resident"))
                .statusCode(200);
    }

    @Test(description = "Delete user, check status code")
    public void deleteUserTest(){
        given()
                .log().all()
                .when()
                .delete(BASE_URL + "users/2")
                .then()
                .log().all()
                .statusCode(204);
    }

    @Test(description = "Register User, check status code, 'id' field, 'token' field")
    public void postRegisterUserTest() {
        Register registerUser = Register.builder()
                .email("eve.holt@reqres.in")
                .password("pistol")
                .build();
        given()
                .body(registerUser)
                .header("Content-type", "application/json")
                .log().all()
                .when()
                .post(BASE_URL + "register")
                .then()
                .log().all()
                .body("id", equalTo(4),
                        "token", equalTo("QpwL5tke4Pnpja7X4"))
                .statusCode(200);
    }

    @Test(description = "(Negative)Unregister User, check status code, check error message")
    public void postUnregisterUserTest() {
        Register registerUser = Register.builder()
                .email("sydney@fife")
                .build();
        given()
                .body(registerUser)
                .header("Content-type", "application/json")
                .log().all()
                .when()
                .post(BASE_URL + "register")
                .then()
                .log().all()
                .body("error", equalTo("Missing password"))
                .statusCode(400);
    }

    @Test(description = "Login User, check status code and 'token' field")
    public void postLoginUserTest() {
        Register registerUser = Register.builder()
                .email("eve.holt@reqres.in")
                .password("cityslicka")
                .build();
        given()
                .body(registerUser)
                .header("Content-type", "application/json")
                .log().all()
                .when()
                .post(BASE_URL + "login")
                .then()
                .log().all()
                .body("token", equalTo("QpwL5tke4Pnpja7X4"))
                .statusCode(200);
    }

    @Test(description = "(Negative)Unlogin User, check status code, check error message")
    public void postUnloginUserTest() {
        Register registerUser = Register.builder()
                .email("peter@klaven")
                .build();
        given()
                .body(registerUser)
                .header("Content-type", "application/json")
                .log().all()
                .when()
                .post(BASE_URL + "register")
                .then()
                .log().all()
                .body("error", equalTo("Missing password"))
                .statusCode(400);
    }

    @Test(description = "Get Delayed response and check status code and all exists fields of the first user")
    public void getDelayedResponseTest() {

        String body =
                given()
                        .log().all()
                        .when()
                        .get(BASE_URL + "users?delay=3")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract().body().asString();

        UserList usersList = new Gson().fromJson(body, UserList.class);
        softAssert.assertEquals(usersList.getData().get(0).getId(), 1);
        softAssert.assertEquals(usersList.getData().get(0).getFirstName(), "George");
        softAssert.assertEquals(usersList.getData().get(0).getLastName(), "Bluth");
        softAssert.assertEquals(usersList.getData().get(0).getAvatar(), "https://reqres.in/img/faces/1-image.jpg");
    }

    public void resultTest() {
        softAssert.assertAll();
    }

}