package com.example.security.Services;

import com.example.security.Repositories.UserRepository;
import com.example.security.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private BCryptPasswordEncoder pwdEncoder;

    @Override
    public Integer save(User user) {

        //TODO TASK ADD PASSWORD ENCODER
        user.setPassword(
                pwdEncoder.encode(
                        user.getPassword()));

        return repo.save(user).getId();
    }

    //get user by username
    @Override
    public Optional<User> findByUsername(String username) {

        return repo.findByUsername(username);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> opt = findByUsername(username);
        if (opt.isEmpty()){
            throw new UsernameNotFoundException("user not exist");
        }

        //read user from DB
        User user = opt.get();

        //Need to return spring User Object

        return new org.springframework.security.core.userdetails.User(username,
                user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role))
                        .collect(Collectors.toList())
        );
    }
}
