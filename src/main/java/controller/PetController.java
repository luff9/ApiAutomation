package controller;

import utils.Constant;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.Pet;
import model.PetStatus;

import java.io.File;
import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class PetController {
    final RequestSpecification specification;

    public PetController() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri(Constant.PET_ENDPOINT);
        requestSpecBuilder.setContentType(ContentType.JSON);
        requestSpecBuilder.log(LogDetail.ALL);
        specification = requestSpecBuilder.build();
    }

    public Pet addPet(Pet pet) {
        return given(specification).body(pet)
                                   .post()
                                   .as(Pet.class);
    }

    public Pet findPet(Pet pet) {
        return given(specification).pathParam("petId", pet.getId())
                                   .get(Constant.PET_ENDPOINT + "/{petId}")
                                   .as(Pet.class);
    }

    public Response searchPet(Pet pet) {
        return given(specification).pathParam("petId", pet.getId())
                                   .get(Constant.PET_ENDPOINT + "/{petId}");
    }


    public void deletePet(Pet pet) {
        given(specification).pathParam("petId", pet.getId())
                            .delete(Constant.PET_ENDPOINT + "/{petId}");
    }

    public ArrayList findPet(PetStatus status) {
        return given(specification).queryParam("status", status)
                                   .get(Constant.PET_ENDPOINT + "/findByStatus")
                                   .as(ArrayList.class);
    }

    public Pet updatePet(Pet pet) {
        return given(specification).body(pet)
                                   .put()
                                   .as(Pet.class);
    }

    public void updateWithBodyPet(Pet pet, String name, PetStatus petStatus) {
        given(specification).header("accept", "application/json")
                            .header("Content-Type", "application/x-www-form-urlencoded")
                            .pathParam("petId", pet.getId())
                            .formParam("name", name)
                            .formParam("status", petStatus)
                            .post(Constant.PET_ENDPOINT + "/{petId}");

    }

    public void updateWithBodyPet(Pet pet, File file) {
        given(specification).header("accept", "application/json")
                            .header("Content-Type", "multipart/form-data")
                            .pathParam("petId", pet.getId())
                            .multiPart("file", file)
                            .multiPart("additionalMetadata", "Akaza")
                            .post(Constant.PET_ENDPOINT + "/{petId}/uploadImage");

    }

}
