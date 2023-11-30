package com.fastcampus.reserve.infrestructure;

import static com.fastcampus.reserve.common.response.ErrorCode.COMMON_SYSTEM_ERROR;
import static com.fastcampus.reserve.common.response.ErrorCode.REDIS_LOCK_ACQUISITION_FAILED;

import com.fastcampus.reserve.common.exception.CustomException;
import com.fastcampus.reserve.domain.RedissonLockService;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RedissonLockServiceImpl implements RedissonLockService {

    private final RedissonClient redissonClient;

    @Override
    @Transactional
    public <T> T multiLockProcess(
            List<String> lockNames,
            long waitTime,
            long leaseTime,
            Supplier<T> process
    ) {
        RLock[] multiLocks = lockNames.stream()
                .map(redissonClient::getLock)
                .toArray(RLock[]::new);

        RLock lock = redissonClient.getMultiLock(multiLocks);

        try {
            boolean isLocked = lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
            if (!isLocked) {
                throw new CustomException("다시 시도해 주세요.", REDIS_LOCK_ACQUISITION_FAILED);
            }

            return process.get();
        } catch (InterruptedException e) {
            throw new CustomException("시스템 오류입니다.", COMMON_SYSTEM_ERROR);
        } finally {
            lock.unlock();
        }
    }

}
