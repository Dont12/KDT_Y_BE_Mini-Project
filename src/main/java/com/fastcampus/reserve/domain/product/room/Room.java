package com.fastcampus.reserve.domain.product.room;

import com.fastcampus.reserve.common.exception.CustomException;
import com.fastcampus.reserve.common.response.ErrorCode;
import com.fastcampus.reserve.domain.BaseTimeEntity;
import com.fastcampus.reserve.domain.product.Product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Room extends BaseTimeEntity {

    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false, length = 100)
    private String checkInTime;

    @Column(nullable = false, length = 100)
    private String checkOutTime;

    @Column(nullable = false)
    private Integer baseGuestCount;

    @Column(nullable = false)
    private Integer maxGuestCount;

    @Column(length = 3000)
    private String roomFacilities;

    @OneToMany(
        mappedBy = "room",
        cascade = CascadeType.ALL, orphanRemoval = true
    )
    private final List<RoomImage> images = new ArrayList<>();

    @Builder
    private Room(Long id, Product product, String name, Integer price, Integer stock,
                 String checkInTime, String checkOutTime, Integer baseGuestCount,
                 Integer maxGuestCount, String roomFacilities) {
        this.id = id;
        this.product = product;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.baseGuestCount = baseGuestCount;
        this.maxGuestCount = maxGuestCount;
        this.roomFacilities = roomFacilities;
    }

    public void addImage(RoomImage image) {
        images.add(image);
    }

    public void registerProduct(Product product) {
        if (!Objects.isNull(this.product)) {
            this.product.getRooms().remove(this);
        }
        this.product = product;
        product.addRoom(this);
    }

    public Optional<String> getFirstImage() {
        return images.stream()
                .map(RoomImage::getUrl)
                .findFirst();
    }

    public String getAddress() {
        return this.product.getAddress();
    }

    public Long getProductId() {
        return this.product.getId();
    }

    public String getProductName() {
        return this.product.getName();
    }

    public String getImageUrl() {
        return getFirstImage()
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_IMAGE));
    }
}
