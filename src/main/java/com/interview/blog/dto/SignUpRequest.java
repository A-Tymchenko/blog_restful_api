package com.interview.blog.dto;

import com.interview.blog.entity.Post;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {
    private Long clientId;
    private String username;
    private String email;
    private String password;
    private Set<Post> posts;
}
