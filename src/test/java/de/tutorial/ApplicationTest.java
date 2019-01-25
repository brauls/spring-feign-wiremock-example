package de.tutorial;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.tutorial.model.Repository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
public class ApplicationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testHelloEndpoint() {
        final String url = "http://localhost:" + port + "/hello";
        final String result = restTemplate.getForObject(url, String.class);
        assertEquals("Hello world!", result);
    }

    @Test
    public void testReposEndpoint() throws IOException {
        stubGetGithubRepos();

        final ObjectMapper mapper = new ObjectMapper();
        final List<Repository> expectedRepos = testRepositories();
        final String expectedResult = mapper.writeValueAsString(expectedRepos);

        final String url = "http://localhost:" + port + "/repos/brauls";
        final String actualResult = restTemplate.getForObject(url, String.class);

        assertEquals(expectedResult, actualResult);
    }

    private void stubGetGithubRepos() throws JsonProcessingException {
        final List<Repository> stubRepos = testRepositories();

        final ObjectMapper mapper = new ObjectMapper();
        final String jsonBody = mapper.writeValueAsString(stubRepos);

        stubFor(get(urlEqualTo("/users/brauls/repos"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/vnd.github.v3+json")
                        .withBody(jsonBody)));
    }

    private List<Repository> testRepositories() {
        final List<Repository> testRepos = new ArrayList<>();
        final Repository repository1 = new Repository();
        repository1.setName("repo1");
        repository1.setUrl("/github/repo1");
        testRepos.add(repository1);
        final Repository repository2 = new Repository();
        repository2.setName("repo1");
        repository2.setUrl("/github/repo1");
        testRepos.add(repository2);
        return testRepos;
    }
}
