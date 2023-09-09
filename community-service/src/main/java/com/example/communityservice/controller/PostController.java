package com.example.communityservice.controller;

import com.example.communityservice.model.entity.Post;
import com.example.communityservice.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    
    @PostMapping
    public ResponseEntity<String> savePost(@RequestBody Post post){
        String response = postService.savePost(post);
        return ResponseEntity.ok().body(response);
    }
    
}
