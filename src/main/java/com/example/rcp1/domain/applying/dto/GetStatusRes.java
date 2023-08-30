package com.example.rcp1.domain.applying.dto;

import com.example.rcp1.domain.applying.domain.Applying;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetStatusRes {
    private Long id;
    private Long user_id;
    private Long post_id;
    private String resume_url;
    private String recommender_email;
    private String status;

    @Builder
    public GetStatusRes(Long id, Long user_id, Long post_id, String resume_url, String recommender_email, String status) {
        this.id = id;
        this.user_id = user_id;
        this.post_id = post_id;
        this.resume_url = resume_url;
        this.recommender_email = recommender_email;
        this.status = status;
    }


    public static GetStatusRes toGetStatusRes(Applying applying) {
        GetStatusRes getStatusRes = new GetStatusRes();
        getStatusRes.setId(applying.getId());
        getStatusRes.setUser_id(applying.getUser().getId());
        getStatusRes.setPost_id(applying.getPost().getId());
        getStatusRes.setResume_url(applying.getResume_url());
        getStatusRes.setRecommender_email(applying.getRecommender_email());
        getStatusRes.setStatus(applying.getStatus());

        return getStatusRes;
    }

}
