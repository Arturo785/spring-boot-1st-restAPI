package com.example.users;


import com.example.users.entities.Role;
import com.example.users.entities.User;
import com.example.users.repositories.RoleRepository;
import com.example.users.repositories.UserRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UsersApplication implements ApplicationRunner {

    private final Faker faker;


    private final UserRepository repository;
    private final RoleRepository roleRepository;

    @Autowired
    public UsersApplication(Faker faker, UserRepository repository, RoleRepository roleRepository) {
        this.faker = faker;
        this.repository = repository;
        this.roleRepository = roleRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(UsersApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Role[] roles = {new Role("ADMIN"), new Role("SUPPORT"), new Role("User")};

        for(Role role : roles){
            roleRepository.save(role);
        }

        for(int i = 0; i < 50; i++){
            User user = new User();
            user.setUsername(faker.name().username());
            user.setPassword(faker.pokemon().name());

            repository.save(user);
        }
    }
}
