package com.pss.project.controller;

import com.pss.project.payload.CommentDto;
import com.pss.project.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class commentController {
    CommentService commentService;

    //Constructor DI
    public commentController(CommentService commentService){
        this.commentService = commentService;
    }

    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> saveComment(@PathVariable("postId") long postId,
                                                 @Valid @RequestBody CommentDto commentDto){
        CommentDto commentDto1 = commentService.saveComment(postId, commentDto);
        return new ResponseEntity<>(commentDto1, HttpStatus.CREATED);
    }
    @GetMapping("/post/{postId}/comments")
    public ResponseEntity<List<CommentDto>> readComment(@PathVariable long postId){
        List<CommentDto> commentDto = commentService.readComment(postId);
        return new ResponseEntity<>(commentDto, HttpStatus.ACCEPTED);
    }

    @GetMapping("/post/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> readCommentbyId(@PathVariable long postId,
                                                      @PathVariable int commentId){
        CommentDto com = commentService.readCommentByCommentId(postId, commentId);
        return new ResponseEntity<>(com, HttpStatus.ACCEPTED);
    }
    @PutMapping("/post/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable("postId") long postId,
                                                    @PathVariable("commentId") int commentId,
                                                    @Valid @RequestBody CommentDto commentDto){

        return new ResponseEntity<>(commentService.updateComment(postId, commentId, commentDto), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/post/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable("postId") long postId,
                                                    @PathVariable("commentId") int commentId){
        String s = commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>(s, HttpStatus.GONE);
    }
}
