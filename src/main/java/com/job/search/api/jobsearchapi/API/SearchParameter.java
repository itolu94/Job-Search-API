package com.job.search.api.jobsearchapi.API;

public class SearchParameter {
    public String title;
    public String state;
    public String city;
    public int page;

    public SearchParameter(String title, String state, String city, int page) {
        this.title = title;
        this.state = state;
        this.city = city;
        this.page = page;
    }

    public SearchParameter(String title, String state, int page) {
        this.title = title;
        this.state = state;
        this.page = page;
    }

    public SearchParameter(String title, String state) {
        this.title = title;
        this.state = state;
        this.page = 1;
    }

    public SearchParameter(String title) {
        this.title = title;
        this.page = 1;
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

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
