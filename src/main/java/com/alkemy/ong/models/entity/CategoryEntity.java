package com.alkemy.ong.models.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@Entity
@Table(name = "categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SQLDelete(sql= "UPDATE categories SET soft_delete = true WHERE category_id=?")
@Where(clause = "soft_delete = false")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @NotNull(message = "Name can't be empty or void.")
    private String name;

    @Column
    private String description;

    @Column
    private String image;

    @CreationTimestamp
    @Column
    private Timestamp timestamp;

    @Column(name = "soft_delete")
    private boolean softDelete = Boolean.FALSE;
}
