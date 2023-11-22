package com.fastcampus.reserve.domain.user;

import com.fastcampus.reserve.domain.user.User.RoleType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Authority {

    @Id
    @Enumerated(EnumType.STRING)
    private RoleType role;

    @Builder
    private Authority(RoleType role) {
        this.role = role;
    }
}
