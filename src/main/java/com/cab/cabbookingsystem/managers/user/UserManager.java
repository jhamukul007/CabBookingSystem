package com.cab.cabbookingsystem.managers.user;

import com.cab.cabbookingsystem.enums.UserType;
import com.cab.cabbookingsystem.models.User;

public interface UserManager {
    User registerUser(String name, String phone, UserType userType);
    User getByPhone(String phone);
}
