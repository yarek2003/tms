package com.example.tms.entity;

import com.example.tms.dto.tasks.Priority;
import com.example.tms.dto.tasks.Status;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "pk")
@Entity(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer pk;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    @JoinColumn
    @ManyToOne
    private User executor;
    @ManyToOne
    @JoinColumn
    private User author;
}
