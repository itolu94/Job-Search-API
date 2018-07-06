package com.job.search.api.jobsearchapi.API;

import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;

@RestController
public class ZipRecruiterAPI {
    @GetMapping(value = "/ziprecruiter")
    //TODO add RequestParameter as @RequestBody
    public ResponseEntity<Object> getZipRecruiter() {
        JSONObject Entity = new JSONObject();
        ArrayList<Object> listings = new ArrayList<Object>();
        try{
            String urlRequest = "https://www.ziprecruiter.com/candidate/search?search=Developer&location=Atlanta%2C+GA";
            Document document = Jsoup.connect(urlRequest).userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .get();
            Elements jobs = document.getElementById("job_list").getElementsByClass("job_result");
            if(jobs.size() > 0){
                for(Element e: jobs) {

                    JSONObject tmp = new JSONObject();
                    String link = e.getElementsByClass("job_link").attr("href");
                    String title = e.getElementsByTag("span").text().trim();
                    String location = e.getElementsByClass("location").text().trim();
                    String source = "ZipRecruiter";
                    tmp.put("title", title);
                    tmp.put("link", link);
                    tmp.put("location", location);
                    tmp.put("source", source);
                    listings.add(tmp);
                }

            }

            Entity.put("jobs", listings);
            return new ResponseEntity<Object>(Entity, HttpStatus.OK);
        }
        catch(IOException | IndexOutOfBoundsException e) {
            System.out.println("Exception was caught: " + e);
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
