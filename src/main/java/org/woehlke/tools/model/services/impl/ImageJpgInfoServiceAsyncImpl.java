package org.woehlke.tools.model.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.tools.model.entities.ImageJpgInfo;
import org.woehlke.tools.model.common.impl.JobEventServiceAsyncImpl;
import org.woehlke.tools.model.services.ImageJpgInfoService;
import org.woehlke.tools.model.services.ImageJpgInfoServiceAsync;

@Service
public class ImageJpgInfoServiceAsyncImpl extends JobEventServiceAsyncImpl<ImageJpgInfo> implements ImageJpgInfoServiceAsync {

    @Autowired
    public ImageJpgInfoServiceAsyncImpl(ImageJpgInfoService imageJpgInfoService) {
        super(imageJpgInfoService);
    }
}
