package com.swjungle.board.comment.repository;

import com.swjungle.board.comment.entity.Comment;
import com.swjungle.board.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByOrderByUpdatedAtDesc();

}