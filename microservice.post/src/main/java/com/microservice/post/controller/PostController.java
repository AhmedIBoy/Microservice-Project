package com.microservice.post.controller;

import com.microservice.post.payload.PostDto;
import com.microservice.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;

    //http://localhost:8081/api/posts
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
        PostDto post = postService.createPost(postDto);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    //http://localhost:8081/api/posts/{postId}
    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable String postId){
        PostDto postById = postService.findPostById(postId);
        return new ResponseEntity<>(postById,HttpStatus.OK);
    }

    //http://localhost:8081/api/posts/{postId}/comment
    @GetMapping("/{postId}/comment")
    public ResponseEntity<PostDto> getPostWithComments(@PathVariable String postId){
       PostDto postDto = postService.getPostWithComments(postId);
       return new ResponseEntity<>(postDto,HttpStatus.OK);
    }
}
