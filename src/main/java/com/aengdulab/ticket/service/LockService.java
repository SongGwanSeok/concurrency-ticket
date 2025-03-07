package com.aengdulab.ticket.service;

import com.aengdulab.ticket.repository.LockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LockService {

    private final LockRepository lockRepository;

    public boolean getLock(String key) {
        return lockRepository.getLock(key);
    }

    public void releaseLock(String key) {
        lockRepository.releaseLock(key);
    }
}
