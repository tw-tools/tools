package org.woehlke.tools.model.db.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.tools.model.db.dao.ImageJpgInfoDao;
import org.woehlke.tools.model.db.entities.ImageJpgInfo;
import org.woehlke.tools.model.db.common.impl.JobEventServiceImpl;
import org.woehlke.tools.model.db.services.ImageJpgInfoService;

@Service
public class ImageJpgInfoServiceImpl extends JobEventServiceImpl<ImageJpgInfo> implements ImageJpgInfoService {

    @Autowired
    public ImageJpgInfoServiceImpl(ImageJpgInfoDao imageJpgInfoDao) {
        super(imageJpgInfoDao);
    }
}
