package com.memesphere.service;

import com.memesphere.domain.FGIndex;
import com.memesphere.repository.FGIndexRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class FGIndexService {

    private final FGIndexRepository fgIndexRepository;

    public FGIndex getIndexByDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        return fgIndexRepository.findByDate(date).orElse(null);
    }
}
