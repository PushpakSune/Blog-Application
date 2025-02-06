package com.pss.project.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private String body;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn (name ="post_id", nullable = false )
    private Post post;
}
