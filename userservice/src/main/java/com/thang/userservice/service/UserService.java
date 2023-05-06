package com.thang.userservice.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.thang.userservice.data.User;
import com.thang.userservice.data.UserRepository;
import com.thang.userservice.model.UserDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public UserDTO login(String username, String password) {
        User user = userRepository.findByUsername(username);
        UserDTO dto = new UserDTO();
        if (user != null) {
            BeanUtils.copyProperties(user,dto);
            if (passwordEncoder.matches(password, user.getPassword())) {
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + (60 * 10000)))
                        .sign(algorithm);
                String refreshToken = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + (10080L*60*10000)))
                        .sign(algorithm);
                dto.setToken(access_token);
                dto.setRefreshToken(refreshToken);
            }
        }
        return dto;
    }
}
