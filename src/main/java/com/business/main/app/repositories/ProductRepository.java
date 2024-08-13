package com.business.main.app.repositories;

import com.business.main.app.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
    List<ProductEntity> findByName(String name);

    @Query("SELECT p FROM ProductEntity p" +
            " WHERE (:priceMin IS NULL OR p.price >= :priceMin)" +
            " AND (:priceMax IS NULL OR p.price <= :priceMax)" +
            " AND (:id IS NULL OR p.id = :id)" +
            " AND (:name IS NULL OR :name = '' OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))")
    Page<ProductEntity> findAllProductEntity(Pageable pageable,
                                             @Param("priceMin") BigDecimal priceMin,
                                             @Param("priceMax") BigDecimal priceMax,
                                             @Param("id") String id,
                                             @Param("name") String name);
}
