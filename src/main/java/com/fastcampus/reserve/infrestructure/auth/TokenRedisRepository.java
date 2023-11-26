package com.fastcampus.reserve.infrestructure.auth;

import com.fastcampus.reserve.domain.auth.Token;
import org.springframework.data.repository.CrudRepository;

public interface TokenRedisRepository extends CrudRepository<Token, String> {

}
