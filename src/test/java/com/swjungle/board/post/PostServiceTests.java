package com.swjungle.board.post;

import com.swjungle.board.post.dto.CreatePostRequest;
import com.swjungle.board.post.dto.PostResponse;
import com.swjungle.board.post.dto.UpdatePostRequest;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    private UpdatePostRequest updatePostRequest;
    private PostResponse postResponse;
    private Post post;
    private LocalDateTime now;

    @BeforeEach
    void setup() {
        now = LocalDateTime.now(); // 현재 시간 가져오기
        post = Post.builder()
                .id(1L).title("Test Title")
                .content("Test Content").link("Test Link")
                .category("Test Category").score(100)
                .author("남청우").password("1234")
                .createdAt(now).updatedAt(now)
                .build();

        createPostRequest = new CreatePostRequest("Test Title", "Test Content", "Test Link", "Test Category", 100, "남청우", "1234");
        updatePostRequest = new UpdatePostRequest("Updated Title", "Updated Content", "Updated Link", "Updated Category", 50, "남청우", "1234");
    }

    @Test
    @DisplayName("Post 생성 성공")
    void createPost() {
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
        assertThat(postResponse.createdAt()).isEqualTo(now);
        assertThat(postResponse.updatedAt()).isEqualTo(now);

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
        assertThat(allPosts.get(0).id()).isEqualTo(1L);
        assertThat(allPosts.get(0).title()).isEqualTo("Test Title");
        assertThat(allPosts.get(0).content()).isEqualTo("Test Content");
        assertThat(allPosts.get(0).link()).isEqualTo("Test Link");
        assertThat(allPosts.get(0).category()).isEqualTo("Test Category");
        assertThat(allPosts.get(0).score()).isEqualTo(100);
        assertThat(allPosts.get(0).author()).isEqualTo("남청우");
        assertThat(allPosts.get(0).createdAt()).isEqualTo(now);
        assertThat(allPosts.get(0).updatedAt()).isEqualTo(now);

        verify(postRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Post 조회 성공")
    void getPostById() {
        // given (준비)
        given(postRepository.findById(1L)).willReturn(Optional.of(post));

        // when (실행)
        PostResponse postResponse = postService.getPostById(1L);

        // then (검증)
        assertThat(postResponse.id()).isEqualTo(1L);
        assertThat(postResponse.title()).isEqualTo("Test Title");
        assertThat(postResponse.content()).isEqualTo("Test Content");
        assertThat(postResponse.link()).isEqualTo("Test Link");
        assertThat(postResponse.category()).isEqualTo("Test Category");
        assertThat(postResponse.score()).isEqualTo(100);
        assertThat(postResponse.author()).isEqualTo("남청우");
        assertThat(postResponse.createdAt()).isEqualTo(now);
        assertThat(postResponse.updatedAt()).isEqualTo(now);

        verify(postRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Post 수정 성공")
    void updatePost() {
        // given (준비)
        given(postRepository.getReferenceById(1L)).willReturn(post);
        Post updatePost = Post.builder()
                .id(1L).title("Updated Title")
                .content("Updated Content").link("Updated Link")
                .category("Updated Category").score(50)
                .author("남청우").password("1234")
                .createdAt(now).updatedAt(now)
                .build();

        given(postRepository.save(any(Post.class))).willReturn(updatePost);

        // when (실행)
        PostResponse postResponse = postService.updatePost(1L, updatePostRequest);

        // then (검증)
        assertThat(postResponse.id()).isEqualTo(1L);
        assertThat(postResponse.title()).isEqualTo("Updated Title"); // 수정된 값 검증
        assertThat(postResponse.content()).isEqualTo("Updated Content");
        assertThat(postResponse.link()).isEqualTo("Updated Link");
        assertThat(postResponse.category()).isEqualTo("Updated Category");
        assertThat(postResponse.score()).isEqualTo(50);
        assertThat(postResponse.author()).isEqualTo("남청우");
        assertThat(postResponse.createdAt()).isEqualTo(now);
        assertThat(postResponse.updatedAt()).isEqualTo(now);

        verify(postRepository, times(1)).getReferenceById(1L);
        verify(postRepository, times(1)).save(any(Post.class));
    }
}
