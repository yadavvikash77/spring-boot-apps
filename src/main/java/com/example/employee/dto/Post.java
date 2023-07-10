package com.example.employee.dto;

import lombok.Data;

@Data
public class Post {
	private int id;
	private int userId;
	private String title;
	private String body;
}
