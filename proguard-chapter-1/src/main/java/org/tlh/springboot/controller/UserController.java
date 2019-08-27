package org.tlh.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tlh.springboot.dto.UserCreateDto;
import org.tlh.springboot.dto.UserDto;
import org.tlh.springboot.service.UserService;

import java.util.List;

/**
 * <br>
 * Created by hu ping on 8/15/2019
 * <p>
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public List<UserDto> list() {
        return this.userService.findAll();
    }

    @PostMapping("/create")
    public boolean addUser(@RequestBody UserCreateDto userCreateDto) {
        return this.userService.addUser(userCreateDto);
    }

}
