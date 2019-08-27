package org.tlh.springboot.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * <br>
 * Created by hu ping on 8/15/2019
 * <p>
 */
@Data
public class UserCreateDto implements Serializable {

    private String name;
    private Integer age;
    private String address;
    private String password;

}
