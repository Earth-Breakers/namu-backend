package univ.earthbreaker.namu.database.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactory;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

class H2Factory {

	private H2Factory() {
	}

	static @NotNull EmbeddedDatabaseFactory embeddedDatabaseFactory() {
		EmbeddedDatabaseFactory embeddedDatabaseFactory = new EmbeddedDatabaseFactory();
		embeddedDatabaseFactory.setDatabaseType(EmbeddedDatabaseType.H2);
		embeddedDatabaseFactory.setDatabaseName("core");
		return embeddedDatabaseFactory;
	}
}
