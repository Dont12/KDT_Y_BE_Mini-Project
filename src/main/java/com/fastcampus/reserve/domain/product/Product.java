package com.fastcampus.reserve.domain.product;

import com.fastcampus.reserve.domain.BaseTimeEntity;
import com.fastcampus.reserve.domain.product.room.Room;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Product extends BaseTimeEntity {

    @Id
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(length = 20)
    private String category;

    @Column(length = 3000)
    private String description;

    @Column(length = 20)
    private String zipCode;

    @Column(length = 100)
    private String address;

    @Column(length = 30)
    private String longitude;

    @Column(length = 30)
    private String latitude;

    @Column(length = 30)
    private String area;

    @Column(length = 30)
    private String sigungu;

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.PERSIST, orphanRemoval = true
    )
    private final List<ProductImage> images = new ArrayList<>();

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.PERSIST, orphanRemoval = true
    )
    private final List<Room> rooms = new ArrayList<>();

    public Product(String name, String category, String description, String zipCode, String address,
                   String longitude, String latitude, String area, String sigungu) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.zipCode = zipCode;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.area = area;
        this.sigungu = sigungu;
    }

    public void addImage(ProductImage image) {
        images.add(image);
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }
}
