package com.courier.trackering.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Store implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private double lat;
    private double lng;

}
