package com.pss.project.controller;

import com.pss.project.payload.PostDto;
import com.pss.project.payload.PostResponse;
import com.pss.project.service.impl.PostServiceImpl;
import com.pss.project.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class PostController {

    PostServiceImpl postService;

    //Contructor DI
    public PostController(PostServiceImpl postService){
        this.postService = postService;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/posts")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto)
    {
        postService.createPostDto(postDto);
        return new ResponseEntity<PostDto>(postDto, HttpStatus.GONE);
    }


    /* Simple API returns simple custom Object and http status
    *
    @GetMapping("/read-post")
    public ResponseEntity<List<PostDto>> readPost(){
        List<PostDto> postDto = postService.readPostDto();
        return new ResponseEntity<>(postDto, HttpStatus.GONE);
    }
    */


    /*New API with Pagination Logic */
    /*
    @GetMapping("/read-post")
    public PostResponse readPost(
            @RequestParam (value = "pageNo",defaultValue = "0", required = false) int pageNo,
            @RequestParam (value = "pageSize", defaultValue ="10" , required = false) int pageSize){
        PostResponse postResponse = postService.readPostDto(pageNo, pageSize);
        return postResponse;
    }
    */

    /*New API with Pagination and Sorting Logic*/

    /*Test Url  "http://localhost:8082/api/read-post?pageNo=0"
    *           "http://localhost:8082/api/read-post?pageNo=0&pageSize=5"
    *           "http://localhost:8082/api/read-post?pageNo=0&pageSize=6&sortBy=title"
    *           "http://localhost:8082/api/read-post?pageNo=0&pageSize=6&sortBy=id&sortDir=desc"*/
    @GetMapping("/read-post")
    public PostResponse readPost(
            @RequestParam (value = "pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
            @RequestParam (value = "pageSize", defaultValue =AppConstants.DEFAULT_PAGE_SIZE , required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,required = false)String sortDir){

        PostResponse postResponse = postService.readPostDto(pageSize, pageNo, sortBy, sortDir);
        return postResponse;
    }

    @GetMapping("/read-post/{id}")
    public ResponseEntity<PostDto> readOnePost(@PathVariable("id") long id){
               PostDto postDto = postService.readOnePost(id);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update-post/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable("id") int id,
                                              @Valid @RequestBody PostDto postDto){
        PostDto updatedpost = postService.updatePost(postDto, id);
        return new ResponseEntity<>(updatedpost, HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete-post/{id}")
    public ResponseEntity<String> deletePost(@PathVariable long id){
        String resp = postService.deletePost(id);
        return new ResponseEntity<>(resp, HttpStatus.GONE);
    }
}
