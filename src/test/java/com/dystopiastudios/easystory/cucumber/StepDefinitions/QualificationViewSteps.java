package com.dystopiastudios.easystory.cucumber.StepDefinitions;

import com.dystopiastudios.easystory.cucumber.SpringIntegrationTest;
import com.dystopiastudios.easystory.resource.QualificationResource;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class QualificationViewSteps extends SpringIntegrationTest {

    QualificationResource qualificationResource=new QualificationResource();

    @Given("i am a user in the qualification view")
    public void i_am_a_user_in_the_qualification_view() {
        qualificationResource.getQualification();
    }
    @Given("i am a user in the qualifications view")
    public void i_am_a_user_in_the_qualifications_view() {
        qualificationResource=null;
    }


    @When("i make a get request to url {string}")
    public void i_make_a_get_request_to_url(String path) throws IOException {
        makeGet(path,qualificationResource);
    }
    @Then("i should receive a status code as {int}")
    public void i_should_receive_a_status_code_as(Integer codeResponse) {
        assertThat(response.value(), is(codeResponse));
    }

    @Then("i should receive a bad status code as {int}")
    public void i_should_receive_a_bad_status_code_as(Integer codeResponse) {
        assertThat(response.value(), is(HttpStatus.BAD_REQUEST.value()));
    }
}
