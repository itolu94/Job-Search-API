package com.job.search.api.jobsearchapi.API;

public class SearchParameter {
    public String title;
    public String state;
    public String city;
    public String page;

    public SearchParameter(String title, String state, String city, String page) {
        this.title = title;
        this.state = state;
        this.city = city;
        this.page = page;
    }


    public String generateURL() {
        return "Hellow world";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
