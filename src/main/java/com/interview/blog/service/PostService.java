package com.interview.blog.service;


import com.interview.blog.entity.Post;
import com.interview.blog.entity.User;
import com.interview.blog.security.UserPrincipal;
import org.springframework.http.ResponseEntity;

public interface PostService {
    ResponseEntity<?> getAllPosts();
    ResponseEntity<?> findPostsByPublisherId(Long id);
    ResponseEntity<?> getPost(Long id);
    ResponseEntity<?> addPost(Post post, UserPrincipal currentUser);
    ResponseEntity<?> updatePost(Long id,Post post, UserPrincipal currentUser);
    ResponseEntity<?> deletePost(Long id, UserPrincipal currentUser);
}
