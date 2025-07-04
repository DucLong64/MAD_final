package com.jobfinder.job_finder.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTOResponse {
    private Long id;
    private String fullName;
    private String email;
    private String role;
    private String token;

}
