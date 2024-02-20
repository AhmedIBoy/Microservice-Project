package com.microservice.comment.controller;

import com.microservice.comment.payload.CommentDto;
import com.microservice.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto){
        CommentDto comment = commentService.createComment(commentDto);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    //http://localhost:9090/api/comment/
    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentDto>> getAllCommentPostById(@PathVariable String postId){
        List<CommentDto> dto = commentService.getAllCommentsPostById(postId);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
}
