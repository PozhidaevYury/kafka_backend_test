package io.fraud.db.dao;

import io.fraud.db.model.Deal;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;

@RegisterBeanMapper(Deal.class)
public interface DealDao {

    @SqlQuery("SELECT * FROM deals WHERE id = ?")
    List<Deal> findById(int id);
}
