package com.test.comment.controller;

import com.test.comment.payload.CommentDto;
import com.test.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test/comments")
public class CommentController {


    @Autowired
    private CommentService commentService;
//http://localhost:8090/api/test/comments
    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto){
        CommentDto comment = commentService.createComment(commentDto);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

//http://localhost:8090/api/test/comments/{postId}
    @GetMapping("{postId}")
    public ResponseEntity<List<CommentDto>> getCommentWithPostId(@PathVariable String postId){
        List<CommentDto> commentWithPostId = commentService.getCommentWithPostId(postId);
        return new ResponseEntity<>(commentWithPostId,HttpStatus.OK);
    }
}
