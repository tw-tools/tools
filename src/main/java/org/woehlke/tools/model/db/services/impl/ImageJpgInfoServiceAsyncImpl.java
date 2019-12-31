package org.woehlke.tools.model.db.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.tools.model.db.entities.ImageJpgInfo;
import org.woehlke.tools.model.db.common.JobEventServiceAsyncImpl;
import org.woehlke.tools.model.db.services.ImageJpgInfoService;
import org.woehlke.tools.model.db.services.ImageJpgInfoServiceAsync;

@Service
public class ImageJpgInfoServiceAsyncImpl extends JobEventServiceAsyncImpl<ImageJpgInfo> implements ImageJpgInfoServiceAsync {

    @Autowired
    public ImageJpgInfoServiceAsyncImpl(ImageJpgInfoService imageJpgInfoService) {
        super(imageJpgInfoService);
    }
}
