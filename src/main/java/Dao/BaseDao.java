package Dao;

import Model.Errors.InternalServerException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Created by eric on 10/18/16.
 */
class BaseDao {
	private static final Logger LOG = LogManager.getLogger(BaseDao.class);
	private final DataSource DATASOURCE;

	BaseDao() {
		HikariConfig config = new HikariConfig();
		String dbUrl = System.getenv("JDBC_DATABASE_URL");
		config.setJdbcUrl(dbUrl != null ? dbUrl : "jdbc:postgresql://ec2-54-243-202-174.compute-1.amazonaws.com:5432/dbeve90fv3htt2");
		config.setUsername("nttptecwetywbt");
		config.setPassword("rGwDbJXFZKHAOQiouxXhP5fLEu");
		config.addDataSourceProperty("sslmode", "require");
		config.setMaximumPoolSize(3);

		DATASOURCE = new HikariDataSource(config);
	}

	Connection getConnection() {
		try {
			return DATASOURCE.getConnection();
		} catch (Exception e) {
			LOG.error(e);
			throw new InternalServerException("Failed to connect to database", e);
		}
	}
}
