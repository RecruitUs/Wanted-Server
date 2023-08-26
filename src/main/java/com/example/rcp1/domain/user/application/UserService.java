package com.example.rcp1.domain.user.application;

import com.example.rcp1.domain.user.domain.User;
import com.example.rcp1.domain.user.domain.repository.UserRepository;
import com.example.rcp1.domain.user.dto.SignUpReq;
import com.example.rcp1.global.BaseResponse;
import com.example.rcp1.global.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


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
}
