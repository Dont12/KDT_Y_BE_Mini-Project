package com.fastcampus.reserve.domain.user;

import com.fastcampus.reserve.domain.BaseTimeEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
    name = "users",
    uniqueConstraints = {
        @UniqueConstraint(name = "email_unique", columnNames = "email")
    }
)
public class User extends BaseTimeEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 100)
    private String nickname;

    @Column(nullable = false, length = 50)
    private String phone;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.PERSIST, orphanRemoval = true
    )
    private final List<Cart> carts = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id")
    )
    private final List<Authority> authorities = new ArrayList<>();

    @Builder
    private User(
            String email,
            String password,
            String nickname,
            String phone
    ) {
        this(email, password, phone, nickname, RoleType.USER);
    }

    @Builder
    private User(
            String email,
            String password,
            String nickname,
            String phone,
            RoleType roleType
    ) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.phone = phone;
        this.authorities.add(
                createAuthority(roleType)
        );
    }

    public void addCart(Cart cart) {
        carts.add(cart);
    }

    public enum RoleType {
        USER, ADMIN;
    }

    private Authority createAuthority(RoleType roleType) {
        return Authority.builder()
                .role(roleType)
                .build();
    }
}
