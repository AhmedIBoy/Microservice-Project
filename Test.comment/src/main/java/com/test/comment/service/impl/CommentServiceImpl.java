package com.test.comment.service.impl;

import com.test.comment.entity.Comment;
import com.test.comment.exception.ResourceNotFoundException;
import com.test.comment.payload.CommentDto;
import com.test.comment.payload.PostDto;
import com.test.comment.repository.CommentRepository;
import com.test.comment.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Override
    public CommentDto createComment(CommentDto commentDto) {
        try {
            PostDto postDto = restTemplate.getForObject("http://TEST-POST-SERVICE/api/test/post/"+commentDto.getPostId(), PostDto.class);
            if (postDto != null) {
                String commentId = UUID.randomUUID().toString();
                commentDto.setId(commentId);
                Comment comment = mapToEntity(commentDto);
                Comment save = commentRepository.save(comment);
                CommentDto dto = mapToDto(save);
                return dto;
            } else {
                // This else block might not ever be reached because RestTemplate will throw an exception if the post is not found
                throw new ResourceNotFoundException("Post Not Found With ID: " + commentDto.getPostId());
            }
        } catch (HttpClientErrorException.NotFound e) {
            // Log the error or take other appropriate action
            throw new ResourceNotFoundException("Post Not Found With ID: " + commentDto.getPostId());
        } catch (RestClientException e) {
            // Handle other RestClient exceptions here
            throw new RuntimeException("Error while calling the Posts API", e);
        }
    }

    @Override
    public List<CommentDto> getCommentWithPostId(String postId) {
        List<Comment> byPostId = commentRepository.findByPostId(postId);
        List<CommentDto> dto = byPostId.stream().map(c -> mapToDto(c)).collect(Collectors.toList());
        return dto;
    }

    public CommentDto mapToDto(Comment comment){
        return modelMapper.map(comment,CommentDto.class);
    }

    public Comment mapToEntity(CommentDto commentDto){
        return modelMapper.map(commentDto,Comment.class);
    }
}
