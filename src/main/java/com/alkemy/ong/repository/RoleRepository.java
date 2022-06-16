package com.alkemy.ong.repository;

import com.alkemy.ong.models.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    List<RoleEntity> findByName(String name);

}
