package com.interview.blog.service.imp;

import com.interview.blog.exception.ResourceNotFoundException;
import com.interview.blog.entity.Post;
import com.interview.blog.enums.Role;
import com.interview.blog.entity.User;
import com.interview.blog.dto.ApiResponse;
import com.interview.blog.repository.PostRepository;
import com.interview.blog.repository.UserRepository;
import com.interview.blog.security.UserPrincipal;
import com.interview.blog.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImp implements PostService{
    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(PostServiceImp.class);

    @Autowired
    public PostServiceImp(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<?> getAllPosts(){
        List<Post> posts = postRepository.findAll();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findPostsByPublisherId(Long id) {
        List<Post> posts = postRepository.findByPublisher(id);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getPost(Long id){
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return new ResponseEntity<>(post, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<?> addPost(Post post, UserPrincipal currentUser){
        User user = userRepository.findById(currentUser.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", 1L));
        post.setUser(user);
        Post newPost =  postRepository.save(post);
        return new ResponseEntity<>(newPost, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> updatePost(Long id,Post post, UserPrincipal currentUser){
        Post postToUpdate = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        if (post.getUser().getUserId().equals(currentUser.getUserId()) && currentUser.getAuthorities().contains(new SimpleGrantedAuthority(Role.PUBLISHER.toString()))){
            postToUpdate.setTitle(post.getTitle());
            postToUpdate.setBody(post.getBody());
            Post updatedPost = postRepository.save(postToUpdate);
            return new ResponseEntity<>(updatedPost, HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponse(false, "You don't have permission to edit this post"), HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<?> deletePost(Long id, UserPrincipal currentUser){
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        if (post.getUser().getUserId().equals(currentUser.getUserId()) && currentUser.getAuthorities().contains(new SimpleGrantedAuthority(Role.PUBLISHER.toString()))){
            postRepository.deleteById(id);
            return new ResponseEntity<>(new ApiResponse(true, "You successfully deleted post"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponse(false, "You don't have permission to delete this post"), HttpStatus.UNAUTHORIZED);
    }

}
