package com.example.rcp1.domain.heart.presentation;

import com.example.rcp1.domain.heart.application.HeartService;
import com.example.rcp1.domain.heart.dto.HeartReq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/heart")
public class HeartController {

    private final HeartService heartService;

    // 좋아요
    @PostMapping("/like/{postId}")
    public ResponseEntity<?> insert(
            @RequestHeader("Authorization") String Authorization,
            @PathVariable("postId") Long postId) {

        String access_token = Authorization.substring(7);


        heartService.insert(access_token, postId);

        return null;

    }

    // 좋아요 취소
    @DeleteMapping("/unlike/{postId}")
    public ResponseEntity<?> delete(
            @RequestHeader("Authorization") String Authorization,
            @PathVariable("postId") Long postId) {

        String access_token = Authorization.substring(7);

        heartService.delete(access_token, postId);

        return null;
    }
}
