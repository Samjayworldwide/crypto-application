package com.percheski.mining.payload.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileResponse {
    private Long numberOfStakes;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String gender;
}
