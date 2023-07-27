package com.example.employee.service.impl;

import com.example.employee.model.Comments;
import com.example.employee.repository.CommentsRepository;
import com.example.employee.service.CommentsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentsServiceImpl implements CommentsService {

    private final CommentsRepository commentsRepository;

    public CommentsServiceImpl(CommentsRepository commentsRepository) {
        this.commentsRepository = commentsRepository;
    }

    @Override
    public void saveComments(List<Comments> commentsList) {
        commentsRepository.saveAll(commentsList);
    }
}
