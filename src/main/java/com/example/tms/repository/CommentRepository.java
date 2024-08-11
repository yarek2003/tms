package com.example.tms.repository;

import com.example.tms.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByTaskPk(Integer pk);
    Comment findFirstByContent(String content);

    void deleteByPkAndTaskPk(int taskPk, int commentId);
    void deleteAllByTaskPk(int taskPk);
}
