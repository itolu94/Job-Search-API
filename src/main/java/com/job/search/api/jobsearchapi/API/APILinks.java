package com.job.search.api.jobsearchapi.API;

public enum APILinks {
    Cybercoders("https://www.cybercoders.com/search/?page=%s&searchterms=%s&searchlocation=%s,%s&newsearch=true&originalsearch=true&sorttype=relevance"),
    Dice("https://www.dice.com/jobs/q-%s-l-%s%s-radius-30-startPage-%s-jobs-limit-7-jobs"),
    ZipRecruiter("https://www.ziprecruiter.com/candidate/search?search=%s&page=%s&location=%s%s");
    private String link;

    APILinks(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}
