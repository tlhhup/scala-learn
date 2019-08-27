package org.tlh.springboot.service;

import org.tlh.springboot.dto.UserCreateDto;
import org.tlh.springboot.dto.UserDto;

import java.util.List;

/**
 * <br>
 * Created by hu ping on 8/15/2019
 * <p>
 */
public interface UserService {

    List<UserDto> findAll();

    boolean addUser(UserCreateDto userCreateDto);

}
