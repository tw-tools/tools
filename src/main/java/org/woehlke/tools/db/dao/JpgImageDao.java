package org.woehlke.tools.db.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.woehlke.tools.db.ImageJpg;

@Repository("jpgImageDao")
public interface JpgImageDao extends PagingAndSortingRepository<ImageJpg,Long> {
}
