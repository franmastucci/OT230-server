package com.alkemy.ong.models.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.GenericGenerator;

@AllArgsConstructor
@Table(
        name = "roles"
)
@NoArgsConstructor
@Data
@Entity
public class RoleEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    
    @NonNull
    @Column(nullable = false)
    private String name;
    
    private String description;
    
    private Timestamp timestamp;
}
