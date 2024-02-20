package com.test.post.Test.post.repository;

import com.test.post.Test.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRespository extends JpaRepository<Post,String> {
}
