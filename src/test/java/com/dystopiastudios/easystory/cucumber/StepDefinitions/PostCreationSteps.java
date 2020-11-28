package com.dystopiastudios.easystory.cucumber.StepDefinitions;

import com.dystopiastudios.easystory.cucumber.SpringIntegrationTest;
import com.dystopiastudios.easystory.resource.SavePostResource;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PostCreationSteps extends SpringIntegrationTest {
    SavePostResource postResource = new SavePostResource();

    @Given("i am a user and entered the correct data")
    public void i_am_a_user_and_entered_the_correct_data() {
        postResource.setTitle("PostCreadonum2");
        postResource.setDescription("Dimensioon");
        postResource.setContent("comidas");
    }

    @Given("i am a user and entered the incorrect data")
    public void i_am_a_user_and_entered_the_incorrect_data() {
        postResource = null;
    }

    @When("i make a post request to {string}")
    public void i_make_a_post_request_to(String path) throws IOException {
        makePost(path, postResource);
    }
    @Then("the response result received should be {int}")
    public void the_response_result_received_should_be(Integer codeResponse) {
        assertThat(response.value(), is(codeResponse));
    }
    @Then("the response result received should be a bad {int}")
    public void the_response_result_received_should_be_a_bad(Integer codeResponse) {
        assertThat(response.value(), is(HttpStatus.BAD_REQUEST.value()));
    }
}
