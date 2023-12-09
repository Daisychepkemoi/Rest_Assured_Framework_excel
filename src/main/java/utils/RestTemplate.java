package utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

import java.util.Map;

public class RestTemplate {
    public static  Response RESPONSE;
    public static  int HTTP_RESPONSE_CODE;
    public static void POST_template(String body, String method){

    RESPONSE = RestAssured.given().header("Content-Type", "application/json")
                .log().all()
                .body(body)

                .post(method).then().extract().response();
        ResponseBody responseBody = RESPONSE.getBody();

        HTTP_RESPONSE_CODE = RESPONSE.getStatusCode();
        System.out.println("HTTP STATUS CODE " + RESPONSE.getStatusCode());
        System.out.println("RESPONSE BODY : " + responseBody.asString());
        System.out.println("**********************************************************************************");
    }
    public static void POST_template_With_Token(String body, String method,String Access_Token){

        RESPONSE = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " +Access_Token)
                .log().all()
                .body(body)

                .post(method).then().extract().response();
        HTTP_RESPONSE_CODE = RESPONSE.getStatusCode();
    }

    public static void GET_Template_With_Param(Map requestParam, String method){
        RESPONSE = RestAssured.given()
                .log().all()
                .params(requestParam)
                .get(method).then().extract().response();
        HTTP_RESPONSE_CODE = RESPONSE.getStatusCode();
    }



    public static void GET_Template_With_No_Params(String method){
        RESPONSE = RestAssured.given()
                .log().all()
                .get(method).then().extract().response();
        HTTP_RESPONSE_CODE = RESPONSE.getStatusCode();
    }

    public static void GET_Template_With_No_Params_With_Access_Token(String method, String Access_token){
        RESPONSE = RestAssured.given().header("Authorization", "Bearer " +Access_token)
                .log().all()
                .get(method).then().extract().response();
        HTTP_RESPONSE_CODE = RESPONSE.getStatusCode();
        ResponseBody responseBody = RESPONSE.getBody();

        HTTP_RESPONSE_CODE = RESPONSE.getStatusCode();
        System.out.println("HTTP STATUS CODE " + RESPONSE.getStatusCode());
        System.out.println("RESPONSE BODY : " + responseBody.asString());
        System.out.println("**********************************************************************************");
    }

    public static void PUT_Template_with_Body(String body,String method){
        RESPONSE = RestAssured.given().header("Content-Type", "application/json")
                .log().all()
                .body(body)

                .put(method).then().extract().response();
        HTTP_RESPONSE_CODE = RESPONSE.getStatusCode();
    }
    public static void PUT_template_With_Token(String body, String method,String Access_Token){

        RESPONSE = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " +Access_Token)
                .log().all()
                .body(body)

                .put(method).then().extract().response();
        HTTP_RESPONSE_CODE = RESPONSE.getStatusCode();
    }
}
