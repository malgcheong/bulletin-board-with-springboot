package com.swjungle.board.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swjungle.board.common.dto.MessageResponse;
import com.swjungle.board.common.security.JwtAuthenticationFilter;
import com.swjungle.board.common.security.JwtTokenProvider;
import com.swjungle.board.post.controller.PostController;
import com.swjungle.board.post.dto.request.CreatePostRequest;
import com.swjungle.board.post.dto.request.UpdatePostRequest;
import com.swjungle.board.post.dto.response.PostResponse;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
public class PostControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context; // Inject WebApplicationContext

    @MockBean
    private PostService postService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    private Post post;
    private CreatePostRequest createPostRequest;
    private UpdatePostRequest updatePostRequest;
    private PostResponse postResponse;
    private ObjectMapper objectMapper;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
//                .apply(springSecurity())
//                .addFilters(new JwtAuthenticationFilter(jwtTokenProvider)) // JwtAuthenticationFilter 추가
                .build();


        now = LocalDateTime.now(); // 현재 시간 가져오기
        objectMapper = new ObjectMapper(); // ObjectMapper 객체 생성 및 초기화
        LocalDateTime now = LocalDateTime.now(); // 현재 시간 가져오기

        post = Post.builder()
                .title("Test Title")
                .content("Test Content").link("Test Link")
                .category("Test Category").score(100)
                .author("남청우").password("1234")
                .build();

        createPostRequest = new CreatePostRequest("Test Title", "Test Content", "Test Link", "Test Category", 100, "남청우", "1234");
        updatePostRequest = new UpdatePostRequest("Test Title", "Test Content", "Test Link", "Test Category", 100, "남청우", "1234");
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

    @Test
    @DisplayName("Post 조회 성공")
    void getPostById() throws Exception{
        // given (준비)
        given(postService.getPostById(1L)).willReturn(postResponse);

        // when (실행)
        ResultActions resultActions = mockMvc.perform(get("/api/post/{id}", 1L));

        // then (검증)
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.post.id").value(postResponse.id())) // EnvelopeResponseDto의 data, post 필드 접근
                .andExpect(jsonPath("$.data.post.title").value(postResponse.title()))
                .andExpect(jsonPath("$.data.post.content").value(postResponse.content()))
                .andExpect(jsonPath("$.data.post.link").value(postResponse.link()))
                .andExpect(jsonPath("$.data.post.category").value(postResponse.category()))
                .andExpect(jsonPath("$.data.post.score").value(postResponse.score()))
                .andExpect(jsonPath("$.data.post.author").value(postResponse.author()))
                .andExpect(jsonPath("$.data.post.created_at", Matchers.notNullValue()))
                .andExpect(jsonPath("$.data.post.updated_at",Matchers.notNullValue()));

        verify(postService, times(1)).getPostById(1L);
    }

    @Test
    @DisplayName("Post 수정 성공")
    void updatePost() throws Exception {
        // given (준비)
        Post updatePost = Post.builder()
                .id(1L).title("Updated Title")
                .content("Updated Content").link("Updated Link")
                .category("Updated Category").score(50)
                .author("남청우").password("1234")
                .createdAt(now).updatedAt(now)
                .build();

        postResponse = PostResponse.fromEntity(updatePost);
        given(postService.updatePost(eq(1L), any(UpdatePostRequest.class))).willReturn(postResponse);

        // when (실행)
        ResultActions resultActions = mockMvc.perform(
                put("/api/post/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatePostRequest))
        );

        // then (검증)
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.post.id").value(postResponse.id())) // EnvelopeResponseDto의 data, post 필드 접근
                .andExpect(jsonPath("$.data.post.title").value(postResponse.title()))
                .andExpect(jsonPath("$.data.post.content").value(postResponse.content()))
                .andExpect(jsonPath("$.data.post.link").value(postResponse.link()))
                .andExpect(jsonPath("$.data.post.category").value(postResponse.category()))
                .andExpect(jsonPath("$.data.post.score").value(postResponse.score()))
                .andExpect(jsonPath("$.data.post.author").value(postResponse.author()))
                .andExpect(jsonPath("$.data.post.created_at", Matchers.notNullValue()))
                .andExpect(jsonPath("$.data.post.updated_at",Matchers.notNullValue()));

        verify(postService, times(1)).updatePost(eq(1L), any(UpdatePostRequest.class));
    }

    @Test
    @DisplayName("Post 삭제 완료")
    void PostControllerTests() throws Exception {
        // given (준비)
        MessageResponse messageResponse = new MessageResponse("삭제 완료");
        given(postService.deletePost(1L)).willReturn(messageResponse);

        // when (실행)
        ResultActions resultActions = mockMvc.perform(delete("/api/post/{id}", 1L));
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.message").value("삭제 완료"));

        // then (검증)
        verify(postService, times(1)).deletePost(1L);
    }
}

