package com.example.security.Controllers;
import com.example.security.Services.UserServiceImpl;
import com.example.security.Utils.JwtUtil;
import com.example.security.model.User;
import com.example.security.model.UserRequest;
import com.example.security.model.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserRestController {

    @Autowired
    private UserServiceImpl service;

    @Autowired
    private JwtUtil util;

    @Autowired
    private AuthenticationManager authenticationManager;

    //save user data to db
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody  User user){
        Integer id = service.save(user);
        String body = "user '"+id+"' created";
        return ResponseEntity.ok(body);
    }

    //Rest Call for /user/login
    @PostMapping("/login")
    public ResponseEntity<UserResponse> loginUser(@RequestBody UserRequest request){

        //authentication handled by authentication manager
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));

        String token = util.generateToken(request.getUsername());
        return ResponseEntity.ok(new UserResponse(token,"Success!!"));

    }

    //Rest Call for Authorised users
    @PostMapping("/welcome")
    public ResponseEntity<String> welcome_page(Principal p){

        return ResponseEntity.ok("Hello User: "+p.getName());
    }
}
