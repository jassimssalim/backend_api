package SocMedApp.backend.controller;

import SocMedApp.backend.model.Comments;
import SocMedApp.backend.model.Posts;
import SocMedApp.backend.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping(value = "/posts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addPost(@RequestParam("userId") Long userId,
                                          @RequestParam("content") String content,
                                          @RequestParam(value = "photo", required = false) MultipartFile file){

        Posts post = new Posts();
            post.setUserId(userId);
            post.setContent(content);

        return postService.addPost(post, file);
    }

    @GetMapping( "/posts")
    public ResponseEntity<List<Posts>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping( "/profiles/{username}/posts")
    public ResponseEntity<List<Posts>> getAllPostByUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok(postService.getAllPostByUsername(username));
    }

    @GetMapping( "/posts/{id}")
    public ResponseEntity<Posts> getPostById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @DeleteMapping("/posts/delete/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable("id") Long id){
        return postService.deletePostById(id);
    }

    @GetMapping("/posts/{id}/comments")
    public ResponseEntity<List<Comments>> getCommentsByPostId(@PathVariable("id") Long postId){
        return ResponseEntity.ok(postService.getCommentsByPostId(postId));
    }

    @PostMapping("/posts/{id}/comments")
    public ResponseEntity<String> addCommentToPost(@PathVariable("id") Long postId,
                                                   @RequestParam("content") String content,
                                                   @RequestParam("userId") Long userId){

        Comments newComment = new Comments();
            newComment.setContent(content);
            newComment.setPostId(postId);
            newComment.setUserId(userId);

        return postService.addCommentToPost(newComment);
    }

    @DeleteMapping("/posts/{id}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable("commentId") Long commentId){
        return postService.deleteComment(commentId);
    }


}
