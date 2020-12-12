package io.fraud.db;

import io.fraud.db.dao.DealDao;
import io.fraud.db.model.Deal;
import io.fraud.kafka.ProjectConfig;
import org.aeonbits.owner.ConfigFactory;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.postgresql.ds.PGSimpleDataSource;

import java.util.List;

public class DbService {

    private final Jdbi jdbi;

    public DbService() {
        ProjectConfig config = ConfigFactory.create(ProjectConfig.class);

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setServerName(config.dbHost());
        dataSource.setPortNumber(Integer.parseInt(config.dbPort()));
        dataSource.setDatabaseName(config.dbName());
        dataSource.setUser(config.dbUser());
        dataSource.setPassword(config.dbPassword());

        this.jdbi = Jdbi.create(dataSource);
        this.jdbi.installPlugin(new SqlObjectPlugin());
    }

    public List<Deal> findDealById(int id) {
        return jdbi.onDemand(DealDao.class).findById(id);
    }
}
