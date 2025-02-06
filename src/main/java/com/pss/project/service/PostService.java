package com.pss.project.service;

import com.pss.project.payload.PostDto;
import com.pss.project.payload.PostResponse;

public interface PostService {

     PostDto createPostDto(PostDto postDto);


     /* Read all without pagination logic
     List<PostDto> readPostDto();  */


     /*Read all with pagination logic
     PostResponse readPostDto(int pageNo, int pageSize);*/


     //Read all with pagination and sorting logic
     //PostResponse readPostDto(int pageNo, int pageSize, String sortBy);
     PostResponse readPostDto(int pageNo, int pageSize, String sortBy, String sortDir);


     PostDto readOnePost(long id);
     String deletePost(long id);
     PostDto updatePost(PostDto postDto, long id);
}
