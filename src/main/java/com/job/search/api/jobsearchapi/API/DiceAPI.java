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
public class DiceAPI {

    @GetMapping(value = "/dice")
    //TODO add RequestParameter as @RequestBody
    public ResponseEntity<Object> getDice() {
        JSONObject Entity = new JSONObject();
        ArrayList<Object> listings = new ArrayList<Object>();
        try{
            String urlRequest = "https://www.dice.com/jobs/q-Javascript-l-Raleigh%2C_NC-radius-30-startPage-2-jobs-limit-7-jobs";
            Document document = Jsoup.connect(urlRequest).get();
            Elements jobs = document.getElementById("serp").getElementsByClass("complete-serp-result-div");
            int index = 0;
            if(jobs.size() > 0){
                for(Element e: jobs) {
                    JSONObject tmp = new JSONObject();
                    String position = "position" + index;
                    String link = "https://www.dice.com" + e.getElementById(position).attr("href");
                    String title =  e.getElementById(position).attr("title");
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
