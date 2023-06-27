package com.example.demo.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User save(String name, int age) {
        return userRepository.save(new User(name, age));
    }

    @Transactional
    public User changeNickname(Long id, String changeName) {
        Optional<User> byId = userRepository.findById(id);

        byId.get().changeName(changeName);
        return userRepository.save(byId.get());
    }
}
