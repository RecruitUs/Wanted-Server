package com.example.rcp1.domain.user.application;

import com.example.rcp1.domain.user.domain.User;
import com.example.rcp1.domain.user.domain.repository.UserRepository;
import com.example.rcp1.domain.user.dto.SignInReq;
import com.example.rcp1.domain.user.dto.SignUpReq;
import com.example.rcp1.global.CustomAuthenticationException;
import com.example.rcp1.global.config.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Value;
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


    public String signIn(SignInReq signInReq) {
        // 인증 과정
//        Optional<User> byEmail = userRepository.findByEmail(signInReq.getEmail());
//        System.out.println("byEmail = " + byEmail);
//        if (!BCrypt.checkpw(password, user.getPassword())) {
//
//            return null;
//        }

        // 이메일을 통해 사용자 정보 조회
        Optional<User> byEmail = userRepository.findByEmail(signInReq.getEmail());

        if (byEmail.isPresent()) {
            User user = byEmail.get();
            if (BCrypt.checkpw(signInReq.getPassword(), user.getPassword())) {
                return JwtUtil.createJwt(signInReq.getEmail(), secret_key, expiredMs);
            } else {
                throw new CustomAuthenticationException("비밀번호가 일치하지 않습니다.");
            }
        }

//        return JwtUtil.createJwt(signInReq.getEmail(), secret_key, expiredMs);

        throw new CustomAuthenticationException("사용자를 찾을 수 없습니다.");
    }


}
