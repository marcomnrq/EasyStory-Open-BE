package com.dystopiastudios.easystory.cucumber.StepDefinitions;

import com.dystopiastudios.easystory.cucumber.SpringIntegrationTest;
import com.dystopiastudios.easystory.resource.BookmarkResource;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpStatus;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BookmarkViewSteps extends SpringIntegrationTest {

    BookmarkResource bookmarkResource = new BookmarkResource();
    @Given("i am a user in the bookmark view")
    public void i_am_a_user_in_the_bookmark_view() {
        bookmarkResource.getUserId();
        bookmarkResource.getPostId();
        bookmarkResource.getCreatedAt();
        bookmarkResource.getUpdatedAt();

    }

    @Given("i am a user in the bookmark views")
    public void i_am_a_user_in_the_bookmark_views(){
          bookmarkResource = null;
    }

    @When("i make a get request to {string}")
    public void i_make_a_get_request_to(String path) throws IOException {
        makeGet(path,bookmarkResource);
    }
    @Then("i should receive a status code {int}")
    public void i_should_receive_a_status_code(Integer codeResponse) {
        assertThat(response.value(), is(codeResponse));
    }

    @Then("i should receive a bad status code {int}")
    public void i_should_receive_a_bad_status_code(Integer codeResponse){
        assertThat(response.value(), is(HttpStatus.BAD_REQUEST.value()));
    }
}
