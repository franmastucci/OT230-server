package com.alkemy.ong.auth.config.seeder;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.alkemy.ong.models.entity.CategoryEntity;
import com.alkemy.ong.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.alkemy.ong.auth.utility.RoleEnum;

import com.alkemy.ong.models.entity.RoleEntity;
import com.alkemy.ong.models.entity.UserEntity;
import com.alkemy.ong.repository.RoleRepository;
import com.alkemy.ong.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DataBaseSeeder {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private PasswordEncoder encoder;



    private static final String PASSWORD = "12345678";
    private static final String HOST_EMAIL = "@test.com";
    private static final String firstNameUser[] = {"Patricia", "Ronald", "Carlos", "Nathaniel", "Caitlin", "Juan", "Thanos", "Will"};
    private static final String lastNameUser[] = {"Brett", "Kathleen", "Brandi", "Craig", "Katrina", "Peralta", "Smith", "Doll"};


    @EventListener
    public void seed(ContextRefreshedEvent event) throws IOException {
        if (roleRepository.findAll().isEmpty()) {
            createRoles();
        }
        if (userRepository.findAll().isEmpty()) {
            createUsers();
        }
        if (categoryRepository.findAll().isEmpty()) {
            createCategories();
        }
    }

    private void createRoles() {
        createRole(1L, RoleEnum.ADMIN);
        createRole(2L, RoleEnum.USER);
    }

    private void createUsers() {
        createUsers(RoleEnum.ADMIN);
        createUsers(RoleEnum.USER);
    }

    private void createUsers(RoleEnum applicationRole) {

        for (int index = 0; index < 8; index++) {
            userRepository.save(
                    UserEntity.builder()
                            .firstName(firstNameUser[index])
                            .lastName(lastNameUser[index])
                            .email(applicationRole.getSimpleRoleName().toLowerCase() + (index + 1) + HOST_EMAIL)
                            .password(encoder.encode(PASSWORD))
                            .roleId(createListRole(applicationRole))
                            .build());
        }
    }

    private Set<RoleEntity> createListRole(RoleEnum applicationRole) {
        Set<RoleEntity> roles = roleRepository.findByName(applicationRole.getFullRoleName());
        return roles;
    }

    private void createRole(Long id, RoleEnum applicationRole) {
        RoleEntity role = new RoleEntity();
        role.setId(id);
        role.setName(applicationRole.getFullRoleName());
        role.setDescription(applicationRole.name());
        roleRepository.save(role);
    }
    private void createCategories(){
        for( int i = 1; i < 6; i++){
            categoryRepository.save(
                    CategoryEntity.builder()
                            .name("Category " + i)
                            .description("Description " + i)
                            .image("url_image" + i)
                            .softDelete(false)
                            .build());
        }
    }

}