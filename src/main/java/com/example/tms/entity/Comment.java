package com.example.tms.entity;

import jakarta.persistence.*;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "pk")
@Entity (name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk;
    @JoinColumn
    @ManyToOne
    Task task;
    @JoinColumn
    @ManyToOne
    User author;
    private String content;
    Long createdAt;

}
