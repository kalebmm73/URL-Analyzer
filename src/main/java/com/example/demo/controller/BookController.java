//package com.example.demo.controller;
//
//import com.example.demo.domain.Book;
//import com.example.demo.service.BookService;
//import lombok.AllArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController //RESTful controller
//@AllArgsConstructor //create constructor automatically
//public class BookController {
//
//    private final BookService bookService;
//
//    @CrossOrigin //allows communication between separate ports
//    @GetMapping("/hello")
//    public String save(){
//
//        return "Hello World";
//
//    }
//
//    @CrossOrigin
//    @PostMapping("/book")
//    public ResponseEntity<?> save(@RequestBody Book book){
//        return new ResponseEntity<>(bookService.create(book), HttpStatus.CREATED);
//
//    }
//
//
//    @CrossOrigin
//    @GetMapping("/books")
//    public ResponseEntity<?> findBooks(){
//        return new ResponseEntity<>(bookService.AllBook(), HttpStatus.OK);
//
//    }
//
//    //remove book
//    //don't need to send back book object
//    @CrossOrigin
//    @DeleteMapping("/book/{id}") //path can be used for multiple methods, in this case DELETE
//    public ResponseEntity<?> remove(@PathVariable Long id) {
//        bookService.remove(id); //actual deletion
//        return new ResponseEntity<>("Book removed", HttpStatus.NO_CONTENT); //return message, status code
//    }
//}

