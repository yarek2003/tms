package com.example.tms.repository;

import com.example.tms.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findTasksByAuthorId(Integer userId);
    List<Task> findTaskByExecutorId(Integer userId);
}
