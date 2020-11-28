package com.dystopiastudios.easystory.cucumber.StepDefinitions;

import com.dystopiastudios.easystory.cucumber.SpringIntegrationTest;
import com.dystopiastudios.easystory.resource.SaveCommentResource;
import com.dystopiastudios.easystory.resource.SavePostResource;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpStatus;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CommentCreationSteps extends SpringIntegrationTest {
    SaveCommentResource commentResource = new SaveCommentResource();


    @Given("i am a reader")
    public void i_am_a_reader() {
        commentResource.setContent("buen cuento");
    }

    @When("i make a post request to path {string}")
    public void i_make_a_post_request_to_path(String path) throws IOException {
        makePost(path, commentResource);
    }

    @Then("the status response code should be {int}")
    public void the_status_response_code_should_be(Integer statusCode) {
        assertThat(response.value(), is(statusCode));
    }

    @Then("the status response code should be a bad {int}")
    public void the_status_response_code_should_be_a_bad(Integer statusCode) {
        assertThat(response.value(), is(HttpStatus.BAD_REQUEST.value()));
    }

    @And("i don't put content in the comment")
    public void iDonTPutContentInTheComment() {
        commentResource=null;
    }
}
