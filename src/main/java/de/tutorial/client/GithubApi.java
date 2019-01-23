package de.tutorial.client;

import de.tutorial.model.Repository;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.List;

public interface GithubApi {
    @RequestLine("GET /users/{username}/repos")
    @Headers({"Accept: application/vnd.github.v3+json"})
    List<Repository> getUserRepos(@Param("username") final String userName);
}
