package com.cab.cabbookingsystem.models;

import com.cab.cabbookingsystem.enums.UserType;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class User extends BaseEntity{
    private String name;
    private String phone;
    private UserType userType;

    public User(String name, String phone, UserType userType) {
        super();
        this.name = name;
        this.phone = phone;
        this.userType = userType;
    }
}
