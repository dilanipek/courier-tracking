package com.courier.trackering.domain.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "courier_location")
@Setter
@Getter
@NoArgsConstructor
public class CourierLocationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long id;

    @Column(name = "courier_id")
    private String courierId;

    @Column(name = "timestamp")
    private Date timestamp;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "distance")
    private Double distance;

    public CourierLocationEntity(Long id, String courierId, Date timestamp, Double latitude, Double longitude, Double distance) {
        this.id = id;
        this.courierId = courierId;
        this.timestamp = timestamp;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
    }
}

