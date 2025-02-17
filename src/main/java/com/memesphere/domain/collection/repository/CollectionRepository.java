package com.memesphere.domain.collection.repository;

import com.memesphere.domain.collection.entity.Collection;
import com.memesphere.domain.memecoin.entity.MemeCoin;
import com.memesphere.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CollectionRepository extends JpaRepository<Collection, Long> {
    List<Collection> findAllByUserId(Long userId);
    Optional<Collection> findByUserAndMemeCoin(User user, MemeCoin memeCoin);

    @Query("SELECT c FROM Collection c " +
            "JOIN c.memeCoin m " +
            "JOIN m.chartDataList cd " +
            "WHERE c.user.id = :userId " +
            "AND cd.recordedTime = (SELECT MAX(cd2.recordedTime) FROM ChartData cd2 WHERE cd2.memeCoin = m) " +
            "ORDER BY " +
            "   CASE WHEN :sortField = 'priceChange' THEN cd.priceChange END DESC, " +
            "   CASE WHEN :sortField = 'volume' THEN cd.volume END DESC, " +
            "   CASE WHEN :sortField = 'price' THEN cd.price END DESC")
    Page<Collection> findWithLatestChartDataSorted(
            @Param("userId") Long userId,
            @Param("sortField") String sortField,
            Pageable pageable);
}
