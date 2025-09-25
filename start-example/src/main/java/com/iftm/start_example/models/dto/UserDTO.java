package com.iftm.start_example.models.dto;

import java.io.Serializable;

import com.iftm.start_example.models.Address;
import com.iftm.start_example.models.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {

    private String id;
    private String name;
    private int age;
    private Address address;

    public UserDTO(User user) {
        this.id = user.getId().toString(); // Convertendo ObjectId para String
        this.name = user.getName();
        this.age = user.getAge();
        this.address = user.getAddress();
    }

}
