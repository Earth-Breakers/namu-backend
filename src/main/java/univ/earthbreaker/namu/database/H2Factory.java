package univ.earthbreaker.namu.database;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactory;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

class H2Factory {

	private H2Factory() {
	}

	static EmbeddedDatabaseFactory embeddedDatabaseFactory() {
		EmbeddedDatabaseFactory embeddedDatabaseFactory = new EmbeddedDatabaseFactory();
		embeddedDatabaseFactory.setDatabaseType(EmbeddedDatabaseType.H2);
		return embeddedDatabaseFactory;
	}
}
