package com.test.post.Test.post.service.impl;

import com.test.post.Test.post.entity.Post;
import com.test.post.Test.post.exception.ResourceNotFoundException;
import com.test.post.Test.post.payload.PostDto;
import com.test.post.Test.post.repository.PostRespository;
import com.test.post.Test.post.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PostRespository postRespository;

    @Autowired
    private RestTemplate restTemplate;
    @Override
    public PostDto createPost(PostDto postDto) {
//=========Random Auto Generate PostId===============
        String postId = UUID.randomUUID().toString();
        postDto.setId(postId);
        Post post = mapToEntity(postDto);
        Post save = postRespository.save(post);
        PostDto dto = mapToDto(save);
        return dto;
    }

    @Override
    public PostDto getPostById(String postId) {
        Post post = postRespository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post Not Found With PostId : " + postId));
        PostDto dto = mapToDto(post);
        return dto;
    }

    @Override
    public PostDto getPostWithAllComments(String postId) {
        Post post = postRespository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("ResourceNotFound With PostId :" + postId));
        ArrayList comments = restTemplate.getForObject("http://TEST-COMMENT-SERVICE/api/test/comments/" + postId, ArrayList.class);
        PostDto postDto = mapToDto(post);
        postDto.setComments(comments);
        return postDto;
    }

    public PostDto mapToDto(Post post){
        return modelMapper.map(post,PostDto.class);
    }

    public Post mapToEntity(PostDto postDto){
        return modelMapper.map(postDto,Post.class);
    }
}
