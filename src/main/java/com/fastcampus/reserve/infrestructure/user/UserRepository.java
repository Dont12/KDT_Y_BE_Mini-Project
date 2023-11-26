package com.fastcampus.reserve.infrestructure.user;

import com.fastcampus.reserve.domain.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    boolean existsByEmail(String email);

    User findByEmail(String email);
}
