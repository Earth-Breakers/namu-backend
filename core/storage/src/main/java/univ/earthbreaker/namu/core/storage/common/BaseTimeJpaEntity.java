package univ.earthbreaker.namu.db.core.common;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseTimeJpaEntity {

	@CreationTimestamp
	@Column(updatable = false)
	protected LocalDateTime createdAt;

	@UpdateTimestamp
	@Column
	protected LocalDateTime updatedAt;
}
