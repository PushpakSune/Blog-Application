package com.pss.project.mapping;

import com.pss.project.payload.PostDto;
import com.pss.project.entity.Post;

public class PostMapper {
    public static PostDto mapToPostDto(Post post, PostDto postDto){
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        return postDto;
    }
    public static Post mapToPost(PostDto postDto, Post post){
        post.setId(postDto.getId());
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }
}
