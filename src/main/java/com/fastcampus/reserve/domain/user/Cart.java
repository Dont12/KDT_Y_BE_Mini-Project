package com.fastcampus.reserve.domain.user;

import com.fastcampus.reserve.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Cart extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private Long roomId;

    @Column(nullable = false)
    private Integer guestCount;

    @Column(nullable = false)
    private LocalDate checkInDate;

    @Column(nullable = false)
    private LocalDate checkOutDate;

    @Builder
    private Cart(
            Long roomId,
            Integer guestCount,
            LocalDate checkInDate,
            LocalDate checkOutDate
    ) {
        this.roomId = roomId;
        this.guestCount = guestCount;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public void registerUser(User user) {
        if (!Objects.isNull(this.user)) {
            this.user.getCarts().remove(this);
        }

        this.user = user;
        user.addCart(this);
    }
}
