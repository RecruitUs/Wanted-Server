package com.example.rcp1.domain.heart.application;

import com.example.rcp1.domain.heart.domain.Heart;
import com.example.rcp1.domain.heart.domain.repository.HeartRepository;
import com.example.rcp1.domain.user.domain.User;
import com.example.rcp1.domain.user.domain.repository.UserRepository;
import com.example.rcp1.domain.user.presentation.UserController;
import com.example.rcp1.global.config.security.util.JwtUtil;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HeartService {

    @Value("${SECRET_KEY}")
    private String secret_key;

    private final HeartRepository heartRepository;
    private final UserRepository userRepository;
//    private final CompanyRepository companyRepository;

    @Transactional
    public String insert(String access_token, Long postId) {

        String email = JwtUtil.getUserEmail(access_token, secret_key);

        Optional<User> optionalUser = userRepository.findByEmail(email);

        User user = optionalUser.get();

        if (heartRepository.findByUser(user).isPresent()) {
            return null;
        }

        Heart heart = Heart.builder()
                .user(user)
                .build();

        heartRepository.save(heart);

        return "success";

    }

    @Transactional
    public void delete(String access_token, Long postId) {

        String email = JwtUtil.getUserEmail(access_token, secret_key);

        User user = userRepository.findByEmail(email).get();

        // Company company = companyRepository.findById(postId);

        Heart heart = heartRepository.findByUser(user).get(); // company 코드 구현되면 findByUserAndCompany로 수정해야함

        heartRepository.delete(heart);
    }
}
