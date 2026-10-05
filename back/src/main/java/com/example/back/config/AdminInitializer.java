package com.example.back.config;

import com.example.back.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements CommandLineRunner {

    private final UserService userService;
    private final String bootstrapAdminLogin;
    private final String bootstrapAdminPassword;

    @Autowired
    public AdminInitializer(
            UserService userService,
            @Value("${bootstrap.admin.login:}") String bootstrapAdminLogin,
            @Value("${bootstrap.admin.password:}") String bootstrapAdminPassword
    ) {
        this.userService = userService;
        this.bootstrapAdminLogin = bootstrapAdminLogin;
        this.bootstrapAdminPassword = bootstrapAdminPassword;
    }

    @Override
    public void run(String... args) throws Exception {
        userService.createAdmin(bootstrapAdminLogin, bootstrapAdminPassword);
    }
}
