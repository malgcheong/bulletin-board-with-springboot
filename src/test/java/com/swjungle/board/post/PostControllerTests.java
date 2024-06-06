package com.swjungle.board.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swjungle.board.post.controller.PostController;
import com.swjungle.board.post.dto.CreatePostRequest;
import com.swjungle.board.post.dto.PostResponse;
import com.swjungle.board.post.entity.Post;
import com.swjungle.board.post.service.PostService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
public class PostControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    private Post post;
    private CreatePostRequest createPostRequest;
    private PostResponse postResponse;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper(); // ObjectMapper 객체 생성 및 초기화
        LocalDateTime now = LocalDateTime.now(); // 현재 시간 가져오기

        post = Post.builder()
                .title("Test Title")
                .content("Test Content").link("Test Link")
                .category("Test Category").score(100)
                .author("남청우").password("1234")
                .build();

        createPostRequest = new CreatePostRequest("Test Title", "Test Content", "Test Link", "Test Category", 100, "남청우", "1234");
        postResponse = new PostResponse(1L, "Test Content", "Test Content", "Test Link", "Test Category", 100, "남청우",now, now);

    }

    @Test
    @DisplayName("Post 생성 성공")
    void createPostRequest() throws Exception {
        // given (준비)
        given(postService.createPost(any(CreatePostRequest.class))).willReturn(postResponse);

        // when (실행)
        ResultActions resultActions = mockMvc.perform(
                post("/api/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createPostRequest)));

//         then (검증)
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.post.id").value(postResponse.id())) // EnvelopeResponseDto의 data, post 필드 접근
                .andExpect(jsonPath("$.data.post.title").value(postResponse.title()))
                .andExpect(jsonPath("$.data.post.content").value(postResponse.content()))
                .andExpect(jsonPath("$.data.post.link").value(postResponse.link()))
                .andExpect(jsonPath("$.data.post.category").value(postResponse.category()))
                .andExpect(jsonPath("$.data.post.score").value(postResponse.score()))
                .andExpect(jsonPath("$.data.post.author").value(postResponse.author()))
                .andExpect(jsonPath("$.data.post.created_at", Matchers.notNullValue()))
                .andExpect(jsonPath("$.data.post.updated_at",Matchers.notNullValue()));

        verify(postService, times(1)).createPost(any(CreatePostRequest.class));
    }

    @Test
    @DisplayName("Post 전체 조회 성공")
    void getAllPosts() throws Exception {
        // given (준비)
        given(postService.getAllPosts()).willReturn(List.of(postResponse));

        // when (실행)
        ResultActions resultActions = mockMvc.perform(get("/api/post"));

        // then (검증)
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.posts[0].id").value(postResponse.id())) // EnvelopeResponseDto의 data, posts 필드 접근
                .andExpect(jsonPath("$.data.posts[0].title").value(postResponse.title()))
                .andExpect(jsonPath("$.data.posts[0].content").value(postResponse.content()))
                .andExpect(jsonPath("$.data.posts[0].link").value(postResponse.link()))
                .andExpect(jsonPath("$.data.posts[0].category").value(postResponse.category()))
                .andExpect(jsonPath("$.data.posts[0].score").value(postResponse.score()))
                .andExpect(jsonPath("$.data.posts[0].author").value(postResponse.author()))
                .andExpect(jsonPath("$.data.posts[0].created_at", Matchers.notNullValue()))
                .andExpect(jsonPath("$.data.posts[0].updated_at",Matchers.notNullValue()));

        verify(postService, times(1)).getAllPosts();
    }
}

