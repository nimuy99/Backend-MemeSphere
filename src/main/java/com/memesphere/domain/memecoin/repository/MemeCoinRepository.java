package com.memesphere.domain.memecoin.repository;

import com.memesphere.domain.memecoin.entity.MemeCoin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface MemeCoinRepository extends JpaRepository<MemeCoin, Long> {
    @Query("SELECT m FROM MemeCoin m " +
            "JOIN m.chartDataList c " +
            "WHERE (LOWER(m.name) LIKE LOWER(CONCAT('%', :searchWord, '%')) " +
            "   OR LOWER(m.symbol) LIKE LOWER(CONCAT('%', :searchWord, '%')) " +
            "   OR :searchWord MEMBER OF m.keywords) " +
            "AND c.recordedTime = (SELECT MAX(c2.recordedTime) FROM ChartData c2 WHERE c2.memeCoin = m) " +
            "ORDER BY " +
            "   CASE WHEN :sortField = 'priceChange' THEN c.priceChange END DESC, " +
            "   CASE WHEN :sortField = 'volume' THEN c.volume END DESC, " +
            "   CASE WHEN :sortField = 'price' THEN c.price END DESC")
    Page<MemeCoin> findWithLatestChartDataSorted(
            @Param("searchWord") String searchWord,
            @Param("sortField") String sortField,
            Pageable pageable);

    @EntityGraph(attributePaths = {"chartData"})
    @NonNull
    Page<MemeCoin> findAll(@NonNull Pageable pageable);

    @EntityGraph(attributePaths = {"chartData"})
    Page<MemeCoin> findByNameContainingIgnoreCaseOrSymbolContainingIgnoreCaseOrKeywordsContainingIgnoreCase(String name, String symbol, String keyword, Pageable pageable);

    Optional<MemeCoin> findByName(String name);
}
