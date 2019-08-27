package org.tlh.springboot.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <br>
 * Created by hu ping on 8/15/2019
 * <p>
 */
@Data
@Entity
@Table(name = "sys_users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String password;

    private Integer age;

    private String address;

}
