package de.tutorial.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Repository {
    private String name;
    @JsonProperty("full_name")
    private String fullName;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Repository{" +
                "name='" + name + '\'' +
                ", fullName='" + fullName + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
