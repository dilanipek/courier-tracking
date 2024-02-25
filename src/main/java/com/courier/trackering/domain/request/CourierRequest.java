package com.courier.trackering.domain.request;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CourierRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private Date timestamp;
    private Double latitude;
    private Double longitude;

}
