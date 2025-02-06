package com.pss.project.service.impl;

import com.pss.project.payload.CommentDto;
import com.pss.project.payload.PostDto;
import com.pss.project.entity.Post;
import com.pss.project.payload.PostResponse;
import com.pss.project.exception.PostNotFound;
import com.pss.project.mapping.PostMapper;
import com.pss.project.repository.PostRepository;
import com.pss.project.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    PostRepository postRepository;
    ModelMapper mapper;

    //Constructor DI
    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper){
        this.postRepository = postRepository;
        this.mapper = mapper;
    }


    //*******  Create/Post API
    @Override
    public PostDto createPostDto(PostDto postDto) {
        Post post = mapToEntity(postDto);
        Post savedPost = postRepository.save(post);

        PostDto savedpostdto = PostMapper.mapToPostDto(savedPost, new PostDto());
        System.out.println(savedpostdto);
        return savedpostdto;
    }

    /* ***** Read/Get API without pagination logic
    @Override
    public List<PostDto> readPostDto(){
       List<Post> postList =  postRepository.findAll();
       List<PostDto> postListDto = postList.stream().map(post ->PostMapper.mapToPostDto(post, new PostDto())).collect(Collectors.toList());
       // PostDto createddto1 = new PostDto(2,"first post","demo","This is first post");
        System.out.println("created In service call : " +postListDto);
        return postListDto;
    }*/

    /* ***** Read/Get API with pagination logic
    @Override
    public PostResponse readPostDto(int pageSize, int pageNo){

        //Create Pageable instance
        Pageable pageable = PageRequest.of(pageSize, pageNo);


        Page<Post> postPage =  postRepository.findAll(pageable);
        //get content for page object
        List<Post> post =  postPage.getContent();

        List<PostDto> postListDto = postPage.stream()
                .map(Post ->PostMapper.mapToPostDto(Post, new PostDto())).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postListDto);
        postResponse.setPageNo(postPage.getNumber());
        postResponse.setPageSize(postPage.getSize());
        postResponse.setTotalElements(postPage.getTotalElements());
        postResponse.setTotalPages(postPage.getTotalPages());
        postResponse.setTotalPages(postPage.getTotalPages());
        postResponse.setLast(postPage.isLast());


        // PostDto createddto1 = new PostDto(2,"first post","demo","This is first post");
        System.out.println("created In service call : " +postListDto);
        return postResponse;
    }
     */


    //     ****** Read/Get API with pagination and sorting logic
     @Override
     //public PostResponse readPostDto(int pageSize, int pageNo, String sortBy){
         public PostResponse readPostDto(int pageSize, int pageNo, String sortBy, String sortDir){

         //Create Pageable instance
         // Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
         // If we want sort by descending order
         // Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());

         // For this we are using ternary operator id it is default order i.e ascending then it will return ascending object
         //else it will return descending order sort object
         Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending() ;
          Pageable pageable = PageRequest.of(pageNo, pageSize, sort);


     Page<Post> postPage =  postRepository.findAll(pageable);
     //get content for page object
     List<Post> post =  postPage.getContent();

     List<PostDto> postListDto = postPage.stream()
     .map(Post ->mapToDto(Post)).collect(Collectors.toList());

     PostResponse postResponse = new PostResponse();
     postResponse.setContent(postListDto);
     postResponse.setPageNo(postPage.getNumber());
     postResponse.setPageSize(postPage.getSize());
     postResponse.setTotalElements(postPage.getTotalElements());
     postResponse.setTotalPages(postPage.getTotalPages());
     postResponse.setTotalPages(postPage.getTotalPages());
     postResponse.setLast(postPage.isLast());


     // PostDto createddto1 = new PostDto(2,"first post","demo","This is first post");
     System.out.println("created In service call : " +postListDto);
     return postResponse;
     }



   @Override
   public PostDto readOnePost(long id){

      // Optional<PostDto> post =  postRepository.findById(id);
       Post postOptionall =  postRepository.findById(id).orElseThrow(() -> new PostNotFound("Post Unavailable","id", id));
       System.out.println("This is Post ---------"+postOptionall);

       Optional<Post> postOptional =  postRepository.findById(id);
       Post post = postOptional.get();
       System.out.println("Total comments associated with this post is  "+post.getComments().size());
       System.out.println("This is Post ---------"+post);
       PostDto postdto = mapToDto(post);
       System.out.println("This is postDTO ---------"+postdto);

      // System.out.println("Output with custom query : "+postRepository.findByIdWithComments(id).get());
       return postdto;
    }

    @Override
    public String deletePost(long id) {
        Post postOptional =  postRepository.findById(id).orElseThrow(() -> new PostNotFound("Post Unavailable","id", id));
        postRepository.deleteById(id);
        return "Post deleted Successfully";

    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post postOptional =  postRepository.findById(id).orElseThrow(() -> new PostNotFound("Post Unavailable","id", id));
        Post post = PostMapper.mapToPost(postDto, new Post());
        Post save = postRepository.save(post);
        PostDto updatedDto = PostMapper.mapToPostDto(save, new PostDto());
        return updatedDto;
    }


    // Mapping using model Mapper External library
    private Post mapToEntity(PostDto postDto){
        Post post= mapper.map(postDto, Post.class);
        return post;
    }
    private PostDto mapToDto(Post post){
       // PostDto postDto = mapper.map(post, PostDto.class);
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());

        // Map comments
        Set<CommentDto> commentDtos = post.getComments().stream().map(comment -> {
            CommentDto commentDto = new CommentDto();
            commentDto.setId(comment.getId());
            commentDto.setName(comment.getName());
            commentDto.setEmail(comment.getEmail());
            commentDto.setBody(comment.getBody());
            commentDto.setPostId(comment.getPost().getId());
            return commentDto;
        }).collect(Collectors.toSet());
        postDto.setComments(commentDtos);

        return postDto;

    }
    public static PostDto mapToPostDto(Post post, PostDto postDto) {
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());

        // Map comments
        Set<CommentDto> commentDtos = post.getComments().stream().map(comment -> {
            CommentDto commentDto = new CommentDto();
            commentDto.setId(comment.getId());
            commentDto.setName(comment.getName());
            commentDto.setEmail(comment.getEmail());
            commentDto.setBody(comment.getBody());
            commentDto.setPostId(comment.getPost().getId());
            return commentDto;
        }).collect(Collectors.toSet());
        postDto.setComments(commentDtos);

        return postDto;
    }

}
