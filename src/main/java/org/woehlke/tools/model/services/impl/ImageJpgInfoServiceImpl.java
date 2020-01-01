package org.woehlke.tools.model.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.tools.model.dao.ImageJpgInfoDao;
import org.woehlke.tools.model.entities.ImageJpgInfo;
import org.woehlke.tools.model.common.impl.JobEventServiceImpl;
import org.woehlke.tools.model.services.ImageJpgInfoService;

@Service
public class ImageJpgInfoServiceImpl extends JobEventServiceImpl<ImageJpgInfo> implements ImageJpgInfoService {

    @Autowired
    public ImageJpgInfoServiceImpl(ImageJpgInfoDao imageJpgInfoDao) {
        super(imageJpgInfoDao);
    }
}
