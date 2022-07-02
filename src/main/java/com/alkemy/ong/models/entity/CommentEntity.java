package com.alkemy.ong.models.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comments")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @NotBlank(message = "The content can't be blank")
    @NotNull(message = "The content can't be null")
    @Column(columnDefinition = "TEXT")
    private String body;

    @Column(name = "news_id")
    private Long newsId;
}
