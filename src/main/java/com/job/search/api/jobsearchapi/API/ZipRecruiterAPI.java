package com.job.search.api.jobsearchapi.API;

import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class ZipRecruiterAPI {
    @GetMapping("/ziprecruiter")
    public ResponseEntity<Object> getZipRecruiter(
            @RequestParam(value = "title", required = true) String jobTitle,
            @RequestParam(value = "page", required = false, defaultValue = "1") String page,
            @RequestParam(value = "state", required = false, defaultValue = "") String state,
            @RequestParam(value = "city", required = false, defaultValue = "") String city) {
        // get link to query ziprecruiter
        APILinks zipRecruiter = APILinks.ZipRecruiter;
        // format link with query parameters
        String url;
        if(city.isEmpty())
            url = String.format(zipRecruiter.getLink(), jobTitle, page, city, state);
        else{
            // add leading comma to state parameter prior to formatting url
            state = "," + state;
            url = String.format(zipRecruiter.getLink(), jobTitle, page, city, state);
        }
        JSONObject Entity = new JSONObject();
        ArrayList<Object> listings = new ArrayList<Object>();
        try{
            System.out.println(url);
            // fetches HTML for url
            Document document = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .get();
            Elements jobs = document.getElementById("job_list").getElementsByClass("job_result");
            if(jobs.size() > 0){
                // loop through HTML to scrape job posting details
                for(Element e: jobs) {
                    JSONObject tmp = new JSONObject();
                    String link = e.getElementsByClass("job_link").attr("href").trim();
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
            // add array of listings to JSONObject
            Entity.put("jobs", listings);
            // return results back
            return new ResponseEntity<Object>(Entity, HttpStatus.OK);
        }
        catch(IOException | IndexOutOfBoundsException e) {
            System.out.println("Exception was caught: " + e);
            e.printStackTrace();
            return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
