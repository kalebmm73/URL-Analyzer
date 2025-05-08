package com.example.demo.controller;
import com.example.demo.domain.Url;
import com.example.demo.service.UrlService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class UrlController {

    private final UrlService urlService;

    @CrossOrigin
    @PostMapping("/url")
    public ResponseEntity<?> save(@RequestBody Url url){
        //console output
        System.out.println("====================USER SAVING URL====================");
        System.out.println("Retrieved userId from frontend: " + url.getName());
        System.out.println("Retrieved URL from frontend: " + url.getUrl());
        //String userId = "tk"; //hard coded userID
        //create String using returned name from JSON for parameter to create function
        String userId = url.getName();
        return new ResponseEntity<>(urlService.create(url,userId), HttpStatus.CREATED);
    }

    @CrossOrigin
    @GetMapping("/urls")
    public ResponseEntity<?> findAllurls(@RequestParam String userId) {
        //String userId = "tk";
        System.out.println("====================REFRESHING URL LIST====================");
        System.out.println("Searching for all saved URLs from: " + userId);
        return new ResponseEntity<>(urlService.findAll(userId), HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/scan")
    public ResponseEntity<?> scan(@RequestBody Url url){
        System.out.println("====================USER SCANNING URL====================");
        System.out.println("Retrieved url from frontend: " + url.getUrl());
        return new ResponseEntity<>(urlService.analyze(url.getUrl()), HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id) {
        System.out.println("====================USER REMOVING URL====================");
        System.out.println("Removing url with id: " + id);
        urlService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @CrossOrigin
    @PostMapping("/edit/{id}") //cors error: changed 2nd var to passed parameter
    public ResponseEntity<?> edit(@PathVariable Long id, @RequestParam String url) {
        System.out.println("====================USER EDITING URL====================");
        System.out.println("Editing url with id: " + id);
        System.out.println("Setting new url to: " + url);
        urlService.update(id, url);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
