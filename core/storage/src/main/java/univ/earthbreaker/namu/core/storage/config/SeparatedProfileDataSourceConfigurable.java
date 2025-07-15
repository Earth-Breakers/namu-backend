package univ.earthbreaker.namu.db.core.config;

import org.springframework.context.annotation.Profile;

import com.zaxxer.hikari.HikariConfig;

public interface SeparatedProfileDataSourceConfigurable<P> {

	@Profile({"local"})
	P embedded();

	@Profile({"local-dev", "dev"})
	P remote(HikariConfig config);
}
