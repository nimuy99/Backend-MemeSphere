package com.memesphere.repository;

import com.memesphere.domain.FGIndex;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface FGIndexRepository extends JpaRepository<FGIndex, Long> {
    Optional<FGIndex> findByDate(LocalDate date);
}

