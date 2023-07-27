package com.example.employee.service;

import com.example.employee.model.Comments;

import java.util.List;

public interface CommentsService {
    public void saveComments(List<Comments> commentsList);
}
