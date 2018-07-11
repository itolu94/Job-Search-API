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
public class CyberCodersAPI {
    @GetMapping("/cybercoders")
    public ResponseEntity<Object> getCyberCoders(
            @RequestParam(value = "title", required = true) String jobTitle,
            @RequestParam(value = "page", required = false, defaultValue = "1") String page,
            @RequestParam(value = "state", required = false, defaultValue = "") String state,
            @RequestParam(value = "city", required = false, defaultValue = "") String city)  {
        // get link to query cybercoder
        APILinks cybercoders = APILinks.Cybercoders;
        // format link with query parameters
        String url = String.format(cybercoders.getLink(), page, jobTitle, state, city);
        JSONObject Entity = new JSONObject();
        ArrayList<Object> listings = new ArrayList<Object>();
        try{
            // fetches HTML for url
            Document document = Jsoup.connect(url).get();
            Elements jobs = document.getElementsByClass("job-listing-item");
            // loop through HTML to scrape job posting details
            for(Element e: jobs) {
                Elements parent = e.getElementsByClass("job-details-container");
                if(parent.size() > 0){
                    JSONObject tmp = new JSONObject();
                    Element child = parent.get(0).getElementsByClass("job-title").get(0);
                    String link = "https://www.cybercoders.com" + child.getElementsByTag("a").attr("href").trim();
                    String title = child.getElementsByTag("a").text().trim();
                    String location = parent.get(0).getElementsByClass("location").text().trim();
                    String source = "Cybercoders";
                    tmp.put("title", title);
                    tmp.put("link", link);
                    tmp.put("location", location);
                    tmp.put("source", source);
                    listings.add(tmp);
                }
            };
            // add array of listings to JSONObject
            Entity.put("jobs", listings);
            // return results back
            return new ResponseEntity<Object>(Entity, HttpStatus.OK);
        }
        catch(IOException  | IndexOutOfBoundsException e) {
            System.out.println("Exception was caught: " + e);
            e.printStackTrace();
            return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
