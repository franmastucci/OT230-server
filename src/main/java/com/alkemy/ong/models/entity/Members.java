package com.alkemy.ong.models.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SQLDelete(sql = "UPDATE members SET is_active = false WHERE members_id = ?")
@Where(clause = "is_active = false")
public class Members {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "members_id", updatable = false)
   private Long id;

   @Column(nullable = false, unique = true, length = 50)
   private String name;

   @Column(name = "facebook_url", unique = true)
   private String facebookUrl;

   @Column(name = "instagram_url", unique = true)
   private String instagramUrl;

   @Column(name = "linkedin_url", unique = true)
   private String linkedinUrl;

   @Column(nullable = false)
   private String image;

   @Column(nullable = false)
   private String description;

   @Column(name = "is_active", nullable = false)
   private boolean isActive;

   @CreationTimestamp
   @Column(name = "creation_date" ,nullable = false, updatable = false)
   private Timestamp create;
}
