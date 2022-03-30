package com.example.demo.src.account.domain;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PatchPasswordReq {
    private int accountIdx;
    private String password;
    private String newPassword;
}
