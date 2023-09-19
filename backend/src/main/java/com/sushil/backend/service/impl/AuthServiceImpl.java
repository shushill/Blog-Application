package com.sushil.backend.service.Impl;

import com.sushil.backend.entity.Roles;
import com.sushil.backend.entity.User;
import com.sushil.backend.exception.BlogAPIException;
import com.sushil.backend.payload.LoginDto;
import com.sushil.backend.payload.RegisterDto;
import com.sushil.backend.repository.RolesRepo;
import com.sushil.backend.repository.UserRepo;
import com.sushil.backend.service.AuthService;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepo userRepo;
    private RolesRepo rolesRepo;

    private PasswordEncoder passwordEncoder;

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepo userRepo,
                           RolesRepo rolesRepo,
                           PasswordEncoder passwordEncoder){
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepo;
        this.rolesRepo = rolesRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String loginDto(LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "User logged in Successfully";
    }

    @Override
    public String registerDto(RegisterDto registerDto){


        Optional<User> userByUsername = userRepo.findByUsername(registerDto.getUsername());

        if (userByUsername.isPresent()) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Username already exists");
        }

        Optional<User> userByEmail = userRepo.findByEmail(registerDto.getEmail());

        if (userByEmail.isPresent()) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Email already exists");
        }




        User user = new User();

        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Roles> roles = new HashSet<>();
        Optional<Roles> userRole = rolesRepo.findByName("ROLE_USER");

        if(userRole.isPresent()){
            roles.add(userRole.get());

        }else{
            Roles roles1 = new Roles();
            roles1.setName("ROLE_USER");
            rolesRepo.save(roles1);
            roles.add(roles1);
        }
        user.setRoles(roles);
        userRepo.save(user);

        return "User registered successfully";

    }
}
