package org.woehlke.tools.db.dao;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.woehlke.tools.db.entity.Protokoll;

@Repository
public interface ProtokollDao extends CrudRepository<Protokoll,Long> {
}
