package com.dystopiastudios.easystory.cucumber.StepDefinitions;

import com.dystopiastudios.easystory.cucumber.SpringIntegrationTest;
import com.dystopiastudios.easystory.resource.SaveUserResource;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpStatus;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class AccountCreationSteps extends SpringIntegrationTest {

    SaveUserResource saveUserResource = new SaveUserResource();
    @Given("i am a user in the register view and filled the data")
    public void i_am_a_user_in_the_register_view_and_filled_the_data() {
        saveUserResource.setUsername("sebastianro");
        saveUserResource.setPassword("passcucumber5");
        saveUserResource.setEmail("sebasti@gmai.com");
        saveUserResource.setFirstName("Cucumber");
        saveUserResource.setLastName("Cucumber.io");
        saveUserResource.setTelephone("7894121231");
    }

    @Given("i am a user in the register view an filled the data incorrectly")
    public void i_am_a_user_in_the_register_view_an_filled_the_data_incorrectly() {
        saveUserResource = null;
    }

    @When("i make a post request to this url {string}")
    public void i_make_a_post_request_to_this_url(String path) throws IOException {
        makePost(path,saveUserResource);
    }
    @Then("the result status should be a response of {int}")
    public void the_result_status_should_be_a_response_of(Integer codeResponse) {
        assertThat(response.value(), is(codeResponse));
    }

    @Then("the result status should be a bad response of {int}")
    public void the_result_status_should_be_a_bad_response_of(Integer codeResponse) {
        assertThat(response.value(), is(HttpStatus.BAD_REQUEST.value()));
    }
}
