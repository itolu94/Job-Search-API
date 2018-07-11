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
public class DiceAPI {
    @GetMapping("/dice")
    public ResponseEntity<Object> getDice(
            @RequestParam(value = "title", required = true) String jobTitle,
            @RequestParam(value = "page", required = false, defaultValue = "1") String page,
            @RequestParam(value = "state", required = false, defaultValue = "") String state,
            @RequestParam(value = "city", required = false, defaultValue = "") String city) {
        // get link to query dice
        APILinks dice = APILinks.Dice;
        String url;
        if(city.isEmpty())
            url = String.format(dice.getLink(), jobTitle, city, state, page);
        else{
            // add leading comma to city parameter prior to formatting url
            // .... dice's url is VERY sensitive!
            city = "," + city;
            url = String.format(dice.getLink(), jobTitle, city, state, page);
        }
        JSONObject Entity = new JSONObject();
        ArrayList<Object> listings = new ArrayList<Object>();
        try{
            // fetches HTML for url
            Document document = Jsoup.connect(url).get();
            Elements jobs = document.getElementById("serp").getElementsByClass("complete-serp-result-div");
            int index = 0;
            // loop through HTML to scrape job posting details
            if(jobs.size() > 0){
                for(Element e: jobs) {
                    JSONObject tmp = new JSONObject();
                    String position = "position" + index;
                    String link = "https://www.dice.com" + e.getElementById(position).attr("href").trim();
                    String title =  e.getElementById(position).attr("title").trim();
                    String location = e.getElementsByClass("jobLoc").text().trim();
                    String source = "Dice";
                    tmp.put("title", title);
                    tmp.put("link", link);
                    tmp.put("location", location);
                    tmp.put("source", source);
                    listings.add(tmp);
                    index++;
                }

            }
            // add array of listings to JSONObject
            Entity.put("jobs", listings);
            //return results back
            return new ResponseEntity<Object>(Entity, HttpStatus.OK);
        }
        catch(IOException | IndexOutOfBoundsException e) {
            System.out.println("Exception was caught: " + e);
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
