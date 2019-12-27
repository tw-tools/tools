package org.woehlke.tools.db.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.woehlke.tools.db.ImageJpg;

@Component
@Repository
public interface JpgImageDao extends PagingAndSortingRepository<ImageJpg,Long> {
}
