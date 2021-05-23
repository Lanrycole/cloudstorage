package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.Mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.Model.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final HashService hashService;

    /**
     *
     * @param userMapper
     * @param hashService
     */
    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    /**
     *
     * @param username
     * @return
     */
    public boolean isUsernameAvailable(String username) {
        return userMapper.getUser(username) == null;
    }

    /**
     *
     * @param user
     * @return
     */
    public int createUser(User user) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);

        return userMapper.addUser(new User(null, user.getUsername(),
                encodedSalt, hashedPassword, user.getFirstName(), user.getLastName()));
    }

    /**
     *
     * @param username
     * @return
     */
    public User getUser(String username) {

        return userMapper.getUser(username);
    }

    /**
     *
     * @return
     */
    public List<User> getAllUsers(){

        return userMapper.getAllUsers();
    }
}
