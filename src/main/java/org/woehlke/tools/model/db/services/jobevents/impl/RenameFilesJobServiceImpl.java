package org.woehlke.tools.model.db.services.jobevents.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.tools.model.db.dao.jobevents.RenameFilesJobDao;
import org.woehlke.tools.model.db.entities.jobevents.RenameFilesJob;
import org.woehlke.tools.model.db.services.impl.JobEventServiceImpl;
import org.woehlke.tools.model.db.services.jobevents.RenameFilesJobService;

@Service
public class RenameFilesJobServiceImpl extends JobEventServiceImpl<RenameFilesJob> implements RenameFilesJobService {

    @Autowired
    public RenameFilesJobServiceImpl(RenameFilesJobDao renameFilesJobDao) {
       super(renameFilesJobDao);
    }
}
