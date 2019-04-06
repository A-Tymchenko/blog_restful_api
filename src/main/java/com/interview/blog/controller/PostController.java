package com.interview.blog.controller;

import com.interview.blog.entity.Post;
import com.interview.blog.security.CurrentUser;
import com.interview.blog.security.UserPrincipal;
import com.interview.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('PUBLISHER')")
    public ResponseEntity<?> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/publisher")
    @PreAuthorize("hasAuthority('PUBLISHER')")
    public ResponseEntity<?> getAllPostsByCurrentUser(@CurrentUser UserPrincipal currentUser) {
        return postService.findPostsByPublisherId(currentUser.getUserId());
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('PUBLISHER')")
    public ResponseEntity<?> addPost(@Valid @RequestBody Post post, @CurrentUser UserPrincipal currentUser) {
        return postService.addPost(post, currentUser);
    }

    @GetMapping("get/{id}")
    @PreAuthorize("hasAuthority('PUBLISHER')")
    public ResponseEntity<?> getPost(@PathVariable(name = "id") Long id) {
        return postService.getPost(id);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('PUBLISHER')")
    public ResponseEntity<?> updatePost(@PathVariable(name = "id") Long id,@Valid @RequestBody Post post, @CurrentUser UserPrincipal currentUser) {
        return postService.updatePost(id,post, currentUser);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('PUBLISHER')")
    public ResponseEntity<?> deletePost(@PathVariable(name = "id") Long id, @CurrentUser UserPrincipal currentUser) {
        return postService.deletePost(id, currentUser);
    }
}
