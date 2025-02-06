package com.pss.project.service.impl;

import com.pss.project.payload.CommentDto;
import com.pss.project.entity.Comment;
import com.pss.project.entity.Post;
import com.pss.project.exception.BlogAPIException;
import com.pss.project.exception.PostNotFound;
import com.pss.project.repository.CommentRepository;
import com.pss.project.repository.PostRepository;
import com.pss.project.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    PostRepository postRepository;
    CommentRepository commentRepository;

    ModelMapper mapper;
    public CommentServiceImpl (PostRepository postRepository, CommentRepository commentRepository,  ModelMapper mapper){
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.mapper =  mapper;
    }

    /* Save Comment Using Post Request
    * */
   @Override
   public CommentDto saveComment(long postId,CommentDto commentDto){

        Comment comment =  mapToEntity(commentDto);

        //Retrieve Post Entity by ID
       Post post = postRepository.findById(postId).orElseThrow(
               ()-> new PostNotFound("Post Not Found","id", postId));

       //set post to comment entity
       comment.setPost(post);

       //save comment entity to DB
       Comment newComment = commentRepository.save(comment);
       System.out.println("Comment received from DB is "+ newComment);

       CommentDto commentDto1= mapToDto(newComment);

       System.out.println("Comment dto received is "+commentDto1);
       return commentDto1;

    }
    /* Read all comments of a post
    * */
    @Override
    public List<CommentDto> readComment(long postId) {
       // Post post = postRepository.findById(postId).orElseThrow(()->new PostNotFound("Post Unavailable"));

        List<Comment> comments = commentRepository.findByPostId(postId);
        List<CommentDto> commentDtoList = comments.stream()
                .map(eachComment ->mapToDto(eachComment)).collect(Collectors.toList());
        return commentDtoList;
    }

    /*
    * Read Comment By  Id
    * Created by Pushpak
    *  */
//    @Override
//    public CommentDto readCommentByCommentId(long postId, int Id) {
//
//        Comment com = commentRepository.findByIdAndPostId(Id, postId)
//                .orElseThrow(() -> new PostNotFound("Comment does not exist with this Comment id : "+Id+ "and postId "+postId+ " please Enter valid ID  "));
//
//        return mapToDto(com);
//    }

    /*
     * Read Comment By  Id
     * Created by Pushpak
     *  */

    @Override
    public CommentDto readCommentByCommentId(long postId, int Id) {
        // retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new PostNotFound("Post", "id", postId));

        // retrieve comment by id
        Comment comment = commentRepository.findById(Id).orElseThrow(() ->
                new PostNotFound("Comment", "id", Id));


        //if(!comment.getPost().getId().equals((long)post.getId()))
        if(comment.getPost().getId() != post.getId())
        {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
         //equals(post.getId())
        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(long postId, int commentId,CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new PostNotFound("invalid PostId ","Post", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new PostNotFound("Invalid CommentId", "Comment", commentId));
        if(comment.getPost().getId()!= post.getId()){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment Does not belong to Post");
        }

        comment.setName(commentDto.getName());
        comment.setEmail(comment.getEmail());
        comment.setBody(commentDto.getBody());


        return mapToDto(commentRepository.save(comment));
    }

    @Override
    public String deleteComment(long postId, int commentId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new PostNotFound("invalid PostId ","Post", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new PostNotFound("Invalid CommentId", "Comment", commentId));
        if(comment.getPost().getId()!= post.getId()){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment Does not belong to Post");
        }

        commentRepository.deleteById(commentId);
        return "Comment Deleted Successful";
    }


    /*
    * Mapping Logic
    * */
    private  Comment mapToEntity(CommentDto commentDto){
        Comment com = mapper.map(commentDto, Comment.class);
//       Comment com = new Comment();
//       com.setId(commentDto.getId());
//       com.setName(commentDto.getName());
//       com.setEmail(commentDto.getEmail());
//       com.setBody(commentDto.getBody());
        return com;
   }
   private CommentDto mapToDto(Comment comment){
       CommentDto comdto = mapper.map(comment, CommentDto.class);

//        CommentDto comdto = new CommentDto();
//        comdto.setId(comment.getId());
//        comdto.setName(comment.getName());
//        comdto.setEmail(comment.getEmail());
//        comdto.setBody(comment.getBody());
        return comdto;
   }

}
