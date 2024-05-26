package com.example.btpsd.bootstrap;

import com.example.btpsd.model.security.Authority;
import com.example.btpsd.model.security.Role;
import com.example.btpsd.model.security.User;
import com.example.btpsd.repositories.security.AuthorityRepository;
import com.example.btpsd.repositories.security.RoleRepository;
import com.example.btpsd.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserDataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private void loadSecurityData() {

        Authority admin = authorityRepository.save(Authority.builder().permission("Admin").build());
        Authority user = authorityRepository.save(Authority.builder().permission("User").build());
        Authority salesAdmin = authorityRepository.save(Authority.builder().permission("SalesAdmin").build());

        Role adminRole = roleRepository.save(Role.builder().name("ADMIN").build());
        Role userRole = roleRepository.save(Role.builder().name("USER").build());
        Role salesAdminRole = roleRepository.save(Role.builder().name("SALES_ADMIN").build());

        adminRole.setAuthorities(new HashSet<>(Set.of(admin, user, salesAdmin)));
        userRole.setAuthorities(new HashSet<>(Set.of(user)));
        salesAdminRole.setAuthorities(new HashSet<>(Set.of(salesAdmin)));

        roleRepository.saveAll(Arrays.asList(adminRole, userRole, salesAdminRole));

        userRepository.save(User.builder()
                .username("local")
                .password(passwordEncoder.encode("local"))
                .role(adminRole)
                .build());

        userRepository.save(User.builder()
                .username("user")
                .password(passwordEncoder.encode("password"))
                .role(userRole)
                .build());


        log.debug("Users Loaded: " + userRepository.count());
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        if (authorityRepository.count() == 0) {
            loadSecurityData();
        }
    }


}