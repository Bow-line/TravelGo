package app.TravelGo.Post;

import app.TravelGo.dto.CreatePostRequest;
import app.TravelGo.dto.CreateUserRequest;
import app.TravelGo.dto.GetPostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("api/posts")
public class PostController {
    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Iterable<Post> getAllPosts() {
        return postService.getPosts();
    }

    @GetMapping("/{post_id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getPost(@PathVariable("post_id") Long postId) {
        Optional<Post> response = postService.getPost(postId);
        if (response.isPresent()) {
            Post post = response.get();
            GetPostResponse postResponse = GetPostResponse.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .build();
            return ResponseEntity.ok(postResponse);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{post_id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> deletePost(@PathVariable("post_id") Long postId) {
        boolean success = postService.deletePost(postId);
        if (success) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    ResponseEntity<Void> createPost(@RequestBody CreatePostRequest request, UriComponentsBuilder builder) {
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .likes(0)
                .build();

        post = postService.createPost(post);

        return ResponseEntity.created(builder.pathSegment("api", "posts", "{id}")
                .buildAndExpand(post.getId()).toUri()).build();
    }

    //TODO getComments, createComment, deleteComment & obvi entity comments


}
