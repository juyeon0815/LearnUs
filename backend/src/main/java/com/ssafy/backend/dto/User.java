package com.ssafy.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    @Id
    private int userId;

    private String name;
    private String email;
    private String password;
    private int ordinalNo;
    private String region;
    private int classNo;
    private String profileUrl;
    private String nickname;
    private String phone;
    private String statusCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trackId")
    private Track track;
}
