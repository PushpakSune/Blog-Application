package com.pss.project.payload;

import com.pss.project.payload.PostDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private List<PostDto> content;
    private int pageNo;
    private  int PageSize;
    private long totalElements;
    private int totalPages;

    private boolean last;

}
