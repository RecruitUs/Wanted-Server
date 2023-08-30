package com.example.rcp1.domain.applying.application;

import com.example.rcp1.domain.applying.domain.Applying;
import com.example.rcp1.domain.applying.domain.repository.ApplyingRepository;
import com.example.rcp1.domain.applying.dto.ApplyReq;
import com.example.rcp1.domain.recruitment.domain.Post;
import com.example.rcp1.domain.recruitment.domain.repository.PostRepository;
import com.example.rcp1.domain.user.domain.User;
import com.example.rcp1.domain.user.domain.repository.UserRepository;
import com.example.rcp1.global.config.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplyingService {


    @Value("${SECRET_KEY}")
    private String secret_key;

    private final ApplyingRepository applyingRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;


    @Transactional
    public Applying apply(String access_token, Long postId, ApplyReq applyReq) {


        String email = JwtUtil.getUserEmail(access_token, secret_key);

        Optional<User> optionalUser = userRepository.findByEmail(email);
        Optional<Post> optionalPost = postRepository.findById(postId);

        User user = optionalUser.get();
        Post post = optionalPost.get();


        Applying applying = Applying.builder()
                .recommender_email(applyReq.getRecommender_email())
                .resume_url(applyReq.getResume_url())
                .post(post)
                .user(user)
                .build();

        // 지원 완료로 상태 변경
        applying.setStatusD();

        applyingRepository.save(applying);


        return applying;
    }

    // 채용 지원 취소
    public void applyCancel(String token, Long applyingId) {

        String email = JwtUtil.getUserEmail(token, secret_key);

        Optional<User> optionalUser = userRepository.findByEmail(email);
        User user = optionalUser.get();

        Optional<Applying> optionalApplying = applyingRepository.findById(applyingId);
        Applying applying = optionalApplying.get();

        if (user == applying.getUser()) {
            applyingRepository.deleteById(applying.getId());
        } else {
            return;
        }



    }
}