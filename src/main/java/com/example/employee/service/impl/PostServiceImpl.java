package com.example.employee.service.impl;

import com.example.employee.model.Post;
import com.example.employee.repository.PostRepository;
import com.example.employee.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public void savePosts(List<Post> posts) {
        postRepository.saveAll(posts);
    }
}
