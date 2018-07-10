package com.job.search.api.jobsearchapi.API;

import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.ArrayList;


@RestController
public class CyberCodersAPI {
    @GetMapping(value = "/cybercoders")
    //TODO add RequestParameter as @RequestBody
    public ResponseEntity<Object> getCyberCoders(
            @RequestParam(value = "title", required = true) String jobTitle,
            @RequestParam(value = "page", required = false, defaultValue = "1") String page,
            @RequestParam(value = "state", required = false, defaultValue = "") String state,
            @RequestParam(value = "city", required = false, defaultValue = "") String city)  {
        SearchParameter searchParameter = new SearchParameter(jobTitle, state, city, page);
        APILinks cybercoders = APILinks.Cybercoders;
        String urlRequest = String.format(cybercoders.getLink(), searchParameter.page, searchParameter.getTitle(), searchParameter.getState(), searchParameter.getCity());
        JSONObject Entity = new JSONObject();
        ArrayList<Object> listings = new ArrayList<Object>();
        try{
//            String urlRequest = "https://www.cybercoders.com/search/?searchterms=python&searchlocation=Raleigh%2C+NC&newsearch=true&originalsearch=true&sorttype=relevance";
            Document document = Jsoup.connect(urlRequest).get();
            Elements jobs = document.getElementsByClass("job-listing-item");
            for(Element e: jobs) {
                Elements parent = e.getElementsByClass("job-details-container");
                if(parent.size() > 0){
                    JSONObject tmp = new JSONObject();
                    Element child = parent.get(0).getElementsByClass("job-title").get(0);
                    String link = "https://www.cybercoders.com" + child.getElementsByTag("a").attr("href");
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
            Entity.put("jobs", listings);
            return new ResponseEntity<Object>(Entity, HttpStatus.OK);
        }
        catch(IOException  | IndexOutOfBoundsException e) {
            System.out.println("Exception was caught: " + e);
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
