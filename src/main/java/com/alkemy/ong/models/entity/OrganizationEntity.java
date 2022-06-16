package com.alkemy.ong.models.entity;


import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@SQLDelete(sql= "UPDATE organizations SET soft_delete = true WHERE id=?")
@Where(clause = "deleted=false")
@Table(name = "organizations")
public class OrganizationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "organization_id")
    private Long id;

    @NonNull
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String image;

    private String address;
    private Integer phone;

    @NonNull
    @Column(nullable = false)
    private String email;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String welcomeText;

    @Column(columnDefinition = "TEXT")
    private String abautUsText;

    @Column(name = "soft_delete")
    private boolean deleted = Boolean.FALSE;

    @CreationTimestamp
    private Timestamp timestamp;
}
