package univ.earthbreaker.namu.database.auth;

import java.time.LocalDateTime;

import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RefreshTokenJpaRepository extends JpaRepository<RefreshTokenJpaEntity, Long> {

	@Modifying
	@Query("UPDATE RefreshTokenJpaEntity rt SET rt.token = :token, rt.expiresIn = :expiresIn WHERE rt.memberNo = :memberNo")
	void updateTokenValueAndExpiresIn(String token, LocalDateTime expiresIn, Long memberNo);

	@Nullable RefreshTokenJpaEntity findByToken(String refreshTokenValue);
}
