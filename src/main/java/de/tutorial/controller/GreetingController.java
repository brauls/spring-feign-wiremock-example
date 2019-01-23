package de.tutorial.controller;

import de.tutorial.client.GithubApi;
import de.tutorial.model.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GreetingController {
    private final GithubApi githubApi;

    public GreetingController(GithubApi githubApi) {
        this.githubApi = githubApi;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello world!";
    }

    @GetMapping("/repos/{user}")
    public List<Repository> repos(@PathVariable("user") String userName) {
        return githubApi.getUserRepos(userName);
    }
}
