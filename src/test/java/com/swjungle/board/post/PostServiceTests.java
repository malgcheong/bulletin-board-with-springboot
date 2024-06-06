package com.swjungle.board.post;

import com.swjungle.board.post.dto.CreatePostRequest;
import com.swjungle.board.post.dto.PostResponse;
import com.swjungle.board.post.entity.Post;
import com.swjungle.board.post.repository.PostRepository;
import com.swjungle.board.post.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class) // JUnit5와 Mockito 결합
public class PostServiceTests {

    @InjectMocks
    private PostService postService;
    @Mock
    private PostRepository postRepository;


    private CreatePostRequest createPostRequest;
    private PostResponse postResponse;
    private Post post;

    @BeforeEach
    void setup() {
        post = Post.builder()
                .title("Test Title")
                .content("Test Content").link("Test Link")
                .category("Test Category").score(100)
                .author("남청우").password("1234")
                .build();

        createPostRequest = new CreatePostRequest("Test Title", "Test Content", "Test Link", "Test Category", 100, "남청우", "1234");
    }

    @Test
    @DisplayName("Post 생성 성공")
    void createTodo() {
        // given (준비)
        given(postRepository.save(any(Post.class))).willReturn(post);

        // when (실행)
        postResponse = postService.createPost(createPostRequest);

        // then (검증)
        assertThat(postResponse.title()).isEqualTo("Test Title");
        assertThat(postResponse.content()).isEqualTo("Test Content");
        assertThat(postResponse.link()).isEqualTo("Test Link");
        assertThat(postResponse.category()).isEqualTo("Test Category");
        assertThat(postResponse.score()).isEqualTo(100);
        assertThat(postResponse.author()).isEqualTo("남청우");
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    @DisplayName("Post 전체 조회 성공")
    void getAllPosts() {
        // given (준비)
        given(postRepository.findAll()).willReturn(List.of(post));

        // when (실행)
        List<PostResponse> allPosts = postService.getAllPosts();

        // then (검증)
        assertThat(allPosts).hasSize(1);
        assertThat(allPosts.get(0)).isEqualTo(post);

    }
}
