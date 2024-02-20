package com.microservice.comment.service.impl;

import com.microservice.comment.entity.Comment;
import com.microservice.comment.exception.ResourceNotFoundException;
import com.microservice.comment.payload.CommentDto;
import com.microservice.comment.payload.PostDto;
import com.microservice.comment.repository.CommentRepository;
import com.microservice.comment.service.CommentService;
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
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RestTemplate restTemplate;
    @Override
    public CommentDto createComment(CommentDto commentDto) throws ResourceNotFoundException{
        try {
            PostDto postDto = restTemplate.getForObject("http://POST-SERVICE/api/posts/"+commentDto.getPostId(), PostDto.class);
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
    public List<CommentDto> getAllCommentsPostById(String postId) {
        List<Comment> byPostId = commentRepository.findByPostId(postId);
        List<CommentDto> collect = byPostId.stream().map(dto -> mapToDto(dto)).collect(Collectors.toList());
        return collect;
    }

    public CommentDto mapToDto(Comment comment){
        return modelMapper.map(comment,CommentDto.class);
    }
    public Comment mapToEntity(CommentDto commentDto){
        return modelMapper.map(commentDto,Comment.class);
    }
}
