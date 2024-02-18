package univ.earthbreaker.namu.database;

import org.springframework.context.annotation.Profile;

import jakarta.transaction.NotSupportedException;

public interface SeparatedProfileDataSourceConfigurable<P> {

	@Profile({"local", "test"})
	P embedded();

	@Profile({"dev", "prod"})
	P remote() throws NotSupportedException;
}
