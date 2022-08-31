import controller.PetController;
import model.Category;
import model.Pet;
import model.PetStatus;
import model.Tag;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.containsString;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

public class PetTest {
    static final String PHOTO_URL = "https://onepiece.fandom.com/tr/wiki/Tony_Tony_Chopper?file=Tony_Tony_Chopper_Anime_Post_Timeskip_Infobox.png";
    static String PHOTO_URL2;
    PetController petController;
    Pet pet;
    File file = null;

    @BeforeClass
    public void beforeClass() {
        petController = new PetController();
        pet = new Pet();
        pet.setId(Integer.valueOf(RandomStringUtils.randomNumeric(7)));
        pet.setCategory(new Category(1, "Pirate"));
        pet.setName("Tony Tony Chopper");
        pet.setPhotoUrls(Collections.singletonList(PHOTO_URL));
        pet.setTags(Collections.singletonList(new Tag(1, "Reindeer")));
        pet.setStatus(PetStatus.available);

        String photoDirectory = new File(System.getProperty("user.dir")).getAbsolutePath();
        PHOTO_URL2 = String.valueOf(new File(photoDirectory + "/src/test/resources/photo/1.png"));
        file = new File(PHOTO_URL2);
    }

    @Test(priority = 1)
    public void addPet() {
        Pet petResponse = petController.addPet(pet);
        assertEquals(petResponse, pet);
    }


    @Test(priority = 2)
    public void findAddedPet() {
        Pet petResponse = petController.findPet(pet);
        assertEquals(petResponse, pet);
    }

    @Test(priority = 3)
    public void findByStatus() {
        ArrayList<Pet> petResponse = petController.findPet(pet.getStatus());
        assertFalse(petResponse.isEmpty());
        //Limited response, my pet is not inside. To find our pet we need to get with unique property
    }

    @Test(priority = 4)
    public void updatePet() {
        pet.setStatus(PetStatus.pending);
        Pet petResponse = petController.updatePet(pet);
        assertEquals(petResponse, pet);
    }

    @Test(priority = 5)
    public void findUpdatedPet() {
        Pet petResponse = petController.findPet(pet);
        assertEquals(petResponse, pet);
    }

    @Test(priority = 6)
    public void updateWithBody() {
        petController.updateWithBodyPet(pet, "Brook", PetStatus.available);
        Pet petResponse = petController.findPet(pet);
        assertEquals(petResponse.getName(), "Brook");
        assertEquals(petResponse.getStatus(), PetStatus.available);
    }

    @Test(priority = 7)
    public void changePhoto() {

        petController.updateWithBodyPet(pet, file);

        Pet petResponse = petController.findPet(pet);
        pet.setPhotoUrls(Collections.singletonList(PHOTO_URL2));
        assertEquals(petResponse, pet);
        assertEquals(petResponse, pet);
    }

    @Test(priority = 8)
    public void deletePet() {
        petController.deletePet(pet);
        petController.searchPet(pet)
                     .then()
                     .body(containsString("Pet not found"));

    }
}

