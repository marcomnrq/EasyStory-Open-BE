package com.dystopiastudios.easystory.cucumber.StepDefinitions;

import com.dystopiastudios.easystory.cucumber.SpringIntegrationTest;
import com.dystopiastudios.easystory.resource.SaveSubscriptionResource;
import com.dystopiastudios.easystory.resource.SubscriptionResource;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpStatus;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SubscriptionAssignationSteps extends SpringIntegrationTest {

    SaveSubscriptionResource subscriptionResource = new SaveSubscriptionResource();
    SubscriptionResource subsResource = new SubscriptionResource();

    @Given("i am a user in the application and i already subscribed to another user")
    public void i_am_a_user_in_the_application_and_i_already_subscribed_to_another_user() {
        subsResource.getUserId();
    }


    @When("i send a get request to {string}")
    public void i_send_a_get_request_to(String path) throws IOException{
        makeGet(path,subsResource);
    }
    @Then("the status code should be a good {int}")
    public void the_status_code_should_be_a_good(Integer codeResponse) {
        assertThat(response.value(), is(codeResponse));
    }
    @Given("i am a user in the application")
    public void i_am_a_user_in_the_application() {
      subscriptionResource=null;
    }

    @When("i send a post request to {string}")
    public void i_send_a_post_request_to(String path) throws IOException {
        makePost(path, subscriptionResource);
    }
    @Then("the status code should be {int}")
    public void the_status_code_should_be(Integer statusCode) {
        assertThat(response.value(), is(statusCode));
    }
    @Then("the status code should be a bad {int}")
    public void the_status_code_should_be_a_bad(Integer statusCode) {
        assertThat(response.value(), is(HttpStatus.BAD_REQUEST.value()));
    }
}
