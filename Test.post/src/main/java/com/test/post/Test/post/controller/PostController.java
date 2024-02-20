package com.test.post.Test.post.controller;

import com.test.post.Test.post.entity.Post;
import com.test.post.Test.post.payload.PostDto;
import com.test.post.Test.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test/post")
public class PostController {


    @Autowired
    private PostService postService;

// http://localhost:8091/api/test/post
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
        PostDto post = postService.createPost(postDto);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

// http://localhost:8091/api/test/post/{postId}
    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable String postId){
        PostDto postById = postService.getPostById(postId);
        return new ResponseEntity<>(postById,HttpStatus.OK);
    }

//http://localhost:8091/api/test/post/{postId}/comment
    @GetMapping("/{postId}/comment")
    public ResponseEntity<PostDto> getPostWithAllComments(@PathVariable String postId){
      PostDto postDto = postService.getPostWithAllComments(postId);
      return new ResponseEntity<>(postDto,HttpStatus.OK);
    }
}
