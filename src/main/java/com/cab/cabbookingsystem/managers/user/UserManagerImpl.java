package com.cab.cabbookingsystem.managers.user;

import com.cab.cabbookingsystem.enums.UserType;
import com.cab.cabbookingsystem.exceptions.UserAlreadyExistException;
import com.cab.cabbookingsystem.models.User;
import com.cab.cabbookingsystem.utils.Loggable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserManagerImpl implements UserManager{
    private final List<User> users;
    private final Loggable logger;

    public UserManagerImpl(Loggable logger) {
        this.logger = logger;
        this.users = new ArrayList<>();
    }

    @Override
    public User registerUser(String name, String phone, UserType userType) {
        User user = getByPhoneAndType(phone, userType);
        if(user != null)
            throw new UserAlreadyExistException("User already exist with phone ");
        User newUser = new User(name, phone, userType);
        users.add(newUser);
        logger.log(userType + " with name " + name + " has been created successfully \n" +
                "Details " + newUser );
        return newUser;
    }

    public User getByPhoneAndType(String phone, UserType userType){
        return users.stream().filter(
                user ->  Objects.deepEquals(phone, user.getPhone() ) &&
                        Objects.deepEquals(userType, user.getUserType())
        ).findFirst().orElse(null);
    }

    @Override
    public User getByPhone(String phone) {
        return users.stream().filter(
                user ->  Objects.deepEquals(phone, user.getPhone() )
        ).findFirst().orElse(null);
    }
}
