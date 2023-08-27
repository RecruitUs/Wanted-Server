package com.example.rcp1.domain.user.application;

import com.example.rcp1.domain.user.domain.User;
import com.example.rcp1.domain.user.domain.repository.UserRepository;
import com.example.rcp1.domain.user.dto.SignUpReq;
import com.example.rcp1.global.BaseResponse;
import com.example.rcp1.global.SuccessCode;
import com.example.rcp1.global.config.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Value("${SECRET_KEY}")
    private String secret_key;

    // 1시간
    private Long expiredMs = 1000 * 60 * 60L;

    // 회원가입
    @Transactional
    public User signUp(SignUpReq signUpReq) {

        User user = User.builder()
                .email(signUpReq.getEmail())
                .password(BCrypt.hashpw(signUpReq.getPassword(), BCrypt.gensalt())) // 패스워드 암호화
                .name(signUpReq.getName())
                .phoneNumber(signUpReq.getPhoneNumber())
                .specializedField(signUpReq.getSpecializedField())
                .career(signUpReq.getCareer())
                .position(signUpReq.getPosition())
                .school(signUpReq.getSchool())
                .job(signUpReq.getJob())
                .status("A")
                .build();

        userRepository.save(user);

        return user;
    }


    public String login(String email, String password) {
        // 인증 과정
        return JwtUtil.createJwt(email, secret_key, expiredMs);
    }


}
