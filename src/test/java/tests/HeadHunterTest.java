package tests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import headhuntero_bjects.VacanciesList;

import headhuntero_bjects.Vacancy;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;


public class HeadHunterTest {

    @Test
    public void qaSearchTest(){
        String body = given()
                .when()
                .log().all()
                .get("https://api.hh.ru/vacancies?text=QA")
                .then()
                .log().all()
                .statusCode(200)
                .extract().body().asString();

        System.out.println("***BODY***");

        System.out.println(body);

        System.out.println("***OBJECT***");
//        Десериализация
        VacanciesList vacanciesList = new Gson().fromJson(body,VacanciesList.class);
        System.out.println(vacanciesList);


    }

    @Test
    public void qaSearchSerTest(){
        String body = given()
                .when()
                .log().all()
                .get("https://api.hh.ru/vacancies?text=QA")
                .then()
                .log().all()
                .statusCode(200)
                .extract().body().asString();

        System.out.println("***BODY***");

        System.out.println(body);

        System.out.println("***OBJECT***");
//
        Vacancy vacancy1 = new Vacancy();
        vacancy1.setName("Frontend Developer");
        vacancy1.setAlternateUrl("URL");

        Vacancy vacancy2 = new Vacancy();
        vacancy2.setName("Backend Developer");
        vacancy2.setAlternateUrl("URL");

        VacanciesList vacanciesList = new VacanciesList();
        ArrayList<Vacancy> vacancies = new ArrayList<>();
        vacancies.add(vacancy1);
        vacancies.add(vacancy2);
        vacanciesList.setItems(vacancies);

        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();

        String json = gson.toJson(vacanciesList);
        System.out.println(json);
    }
}
