package com.example.employee.restClient.impl;

import com.example.employee.dto.Post;
import com.example.employee.restClient.GetToDoListRestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
@Service
@Slf4j
public class GetToDoListRestTemplateImpl implements GetToDoListRestTemplate {

    private RestTemplate restTemplate;
    private final String api_url = "https://jsonplaceholder.typicode.com/posts/";


    public GetToDoListRestTemplateImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    @Override
    public List<Post> getPosts() {
        Post[] postList = restTemplate.getForObject(api_url, Post[].class);
        return Arrays.asList(postList);
    }
}
