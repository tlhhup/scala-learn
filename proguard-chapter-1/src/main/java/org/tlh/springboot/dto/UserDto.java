package org.tlh.springboot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <br>
 * Created by hu ping on 8/15/2019
 * <p>
 */
@Data
public class UserDto implements Serializable {

    private Integer id;
    @JsonProperty(value = "user_name")
    private String name;
    private Integer age;
    private String address;

}
