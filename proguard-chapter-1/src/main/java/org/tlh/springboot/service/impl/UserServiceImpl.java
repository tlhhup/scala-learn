package org.tlh.springboot.service.impl;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.tlh.springboot.dto.UserCreateDto;
import org.tlh.springboot.dto.UserDto;
import org.tlh.springboot.entity.User;
import org.tlh.springboot.repository.UserRepository;
import org.tlh.springboot.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <br>
 * Created by hu ping on 8/15/2019
 * <p>
 */
@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserDto> findAll() {
        List<User> users = this.userRepository.findAll();
        if (!ObjectUtils.isEmpty(users)) {
            List<UserDto> results = users.parallelStream().map(item -> {
                log.info(item.toString());
                UserDto dto = new UserDto();
                BeanUtils.copyProperties(item, dto);
                log.info(dto.toString());
                return dto;
            }).collect(Collectors.toList());
            return results;
        }
        return Lists.newArrayList();
    }

    @Override
    public boolean addUser(UserCreateDto userCreateDto) {
        try {
            log.info(userCreateDto.toString());
            User user = new User();
            BeanUtils.copyProperties(userCreateDto, user);
            log.info(user.toString());
            this.userRepository.save(user);
            return true;
        } catch (Exception e) {
            log.error("Add User Error", e);
        }
        return false;
    }

}
