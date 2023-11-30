package com.fastcampus.reserve.domain;

import java.util.List;
import java.util.function.Supplier;
import org.springframework.transaction.annotation.Transactional;

public interface RedissonLockService {

    @Transactional
    <T> T multiLockProcess(
            List<String> lockNames,
            long waitTime,
            long leaseTime,
            Supplier<T> process
    );
}
