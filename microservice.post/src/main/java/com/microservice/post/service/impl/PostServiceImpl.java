package com.microservice.post.service.impl;

import com.microservice.post.entity.Post;
import com.microservice.post.exception.ResourceNotFoundException;
import com.microservice.post.payload.Comment;
import com.microservice.post.payload.PostDto;
import com.microservice.post.repository.PostRepository;
import com.microservice.post.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public PostDto createPost(PostDto postDto) {
        //Generate Id For Post Using UUID.Random
        String postId = UUID.randomUUID().toString();
        postDto.setId(postId);
        Post post = mapToEntity(postDto);
        Post savedPost = postRepository.save(post);
        PostDto dto = mapToDto(savedPost);
        return dto;
    }

    @Override
    public PostDto findPostById(String postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Resource Not Found with : " + postId));
        PostDto postDto = mapToDto(post);
        return postDto;
    }

    @Override
    public PostDto getPostWithComments(String postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("ResourceNotFound With PostId : " + postId));
        ArrayList commentList = restTemplate.getForObject("http://COMMENT-SERVICE/api/comment/" + postId, ArrayList.class);
//      Set Post Details In PostDto Witch is Came From Data Base In (post)
//        PostDto postDto = mapToDto(post);
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setAbout(post.getAbout());
        postDto.setComments(commentList);
        return postDto;
    }

    public PostDto mapToDto(Post post){
        return modelMapper.map(post,PostDto.class);
    }
    public Post mapToEntity(PostDto postDto){
        return modelMapper.map(postDto,Post.class);
    }
}
