package com.apishoppage.api.repository;

import com.apishoppage.api.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshTokenEntity, UUID> {

    Optional<RefreshTokenEntity> findByRefreshToken(String refreshToken);
}
