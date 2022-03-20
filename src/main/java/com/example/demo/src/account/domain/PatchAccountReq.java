package com.example.demo.src.account.domain;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PatchAccountReq {
    private int accountIdx;
    private String updateParam;
}
