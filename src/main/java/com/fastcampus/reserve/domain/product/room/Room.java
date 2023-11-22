package com.fastcampus.reserve.domain.product.room;

import com.fastcampus.reserve.domain.BaseTimeEntity;
import com.fastcampus.reserve.domain.product.Product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Room extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

	@Column(nullable = false, length = 255)
    private String name;

	@Column(nullable = false)
	private Integer price;

	@Column(nullable = false)
	private LocalTime checkInTime;

	@Column(nullable = false)
	private LocalTime checkOutTime;

	private Integer baseGuestCount;
	private Integer maxGuestCount;

	@Embedded
	private RoomFacilities roomFacilities;

	@OneToMany(
			mappedBy = "room",
			cascade = CascadeType.PERSIST, orphanRemoval = true
	)
	private final List<RoomImage> images = new ArrayList<>();

	@Builder
	private Room(
			String name,
			Integer price,
			LocalTime checkInTime,
			LocalTime checkOutTime,
			Integer baseGuestCount,
			Integer maxGuestCount,
			RoomFacilities roomFacilities
	) {
		this.name = name;
		this.price = price;
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
}
