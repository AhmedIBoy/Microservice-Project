package com.test.post.Test.post.service;

import com.test.post.Test.post.payload.PostDto;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostDto getPostById(String postId);

    PostDto getPostWithAllComments(String postId);
}
