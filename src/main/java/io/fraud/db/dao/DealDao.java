package io.fraud.db.dao;

import io.fraud.db.model.Deal;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

@RegisterBeanMapper(Deal.class)
public interface DealDao {

    @SqlQuery("SELECT * FROM deals WHERE id = ?")
    Deal findById(int id);
}
