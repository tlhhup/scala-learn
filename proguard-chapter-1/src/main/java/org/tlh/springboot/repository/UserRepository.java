package org.tlh.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tlh.springboot.entity.User;

/**
 * <br>
 * Created by hu ping on 8/15/2019
 * <p>
 */
public interface UserRepository extends JpaRepository<User, Integer> {
}
