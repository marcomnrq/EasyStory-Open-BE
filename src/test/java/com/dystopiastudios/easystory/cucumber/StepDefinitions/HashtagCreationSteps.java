package com.dystopiastudios.easystory.cucumber.StepDefinitions;

import com.dystopiastudios.easystory.cucumber.SpringIntegrationTest;
import com.dystopiastudios.easystory.resource.SaveHashtagResource;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class HashtagCreationSteps extends SpringIntegrationTest {
    SaveHashtagResource saveHashtagResource = new SaveHashtagResource();

    @Given("i am a user and i entered the correct data")
    public void i_am_a_user_and_i_entered_the_correct_data() {
        saveHashtagResource.setName("Comidas");
    }

    @When("i make a post request to this path {string}")
    public void i_make_a_post_request_to_this_path(String path)throws IOException {
        makePost(path, saveHashtagResource);
    }
    @Then("the response status result received should be {int}")
    public void the_response_status_result_received_should_be(Integer codeResponse) {
        assertThat(response.value(), is(codeResponse));
    }

    @Given("i am a user and i entered the incorrect data")
    public void i_am_a_user_and_i_entered_the_incorrect_data() {
        saveHashtagResource=null;
    }


    @Then("the response status result received should be a bad {int}")
    public void the_response_status_result_received_should_be_a_bad(Integer codeResponse) {
        assertThat(response.value(), is(HttpStatus.BAD_REQUEST.value()));
    }
}
