package org.woehlke.tools.db.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.woehlke.tools.db.entity.JpgImage;

public interface JpgImageDao extends PagingAndSortingRepository<JpgImage,Long> {
}
