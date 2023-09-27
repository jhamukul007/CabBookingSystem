package com.cab.cabbookingsystem.models;

import lombok.Data;

import java.util.UUID;
@Data
public class BaseEntity {
    private String id;

    public BaseEntity() {
        this.id = UUID.randomUUID().toString();
    }
}
