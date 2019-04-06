package com.interview.blog.dto;

import com.interview.blog.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType;
    private Long expirationTimeMs;
    private LocalDate dateCreate;
    private Set<Role> role;

    public JwtAuthenticationResponse(String accessToken, LocalDate dateCreate) {
        this.accessToken = accessToken;
        this.dateCreate = dateCreate;
    }

}
