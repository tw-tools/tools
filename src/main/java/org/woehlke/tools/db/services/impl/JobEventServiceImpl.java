package org.woehlke.tools.db.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.tools.db.*;
import org.woehlke.tools.db.dao.*;
import org.woehlke.tools.db.services.JobEventService;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Service
public class JobEventServiceImpl implements JobEventService {

    private final JobEventScaleImagesJobDao jobEventScaleImagesJobDao;
    private final JobEventRenameFilesJobDao jobEventRenameFilesJobDao;
    private final JobEventRenamedOneFileDao jobEventRenamedOneFileDao;
    private final JobEventRenamedOneDirectoryDao jobEventRenamedOneDirectoryDao;


    @Autowired
    public JobEventServiceImpl(JobEventScaleImagesJobDao jobEventScaleImagesJobDao, JobEventRenameFilesJobDao jobEventRenameFilesJobDao, JobEventRenamedOneFileDao jobEventRenamedOneFileDao, JobEventRenamedOneDirectoryDao jobEventRenamedOneDirectoryDao) {
        this.jobEventScaleImagesJobDao = jobEventScaleImagesJobDao;
        this.jobEventRenameFilesJobDao = jobEventRenameFilesJobDao;
        this.jobEventRenamedOneFileDao = jobEventRenamedOneFileDao;
        this.jobEventRenamedOneDirectoryDao = jobEventRenamedOneDirectoryDao;
    }


    @Override
    @Transactional(propagation=REQUIRES_NEW)
    public JobEventScaleImagesJob add(JobEventScaleImagesJob p) {
        return jobEventScaleImagesJobDao.save(p);
    }

    @Override
    @Transactional(propagation=REQUIRES_NEW)
    public JobEventRenameFilesJob add(JobEventRenameFilesJob p) {
        return jobEventRenameFilesJobDao.save(p);
    }

    @Override
    @Transactional(propagation=REQUIRES_NEW)
    public JobEventRenamedOneFile add(JobEventRenamedOneFile p) {
        return jobEventRenamedOneFileDao.save(p);
    }

    @Override
    @Transactional(propagation=REQUIRES_NEW)
    public JobEventRenamedOneDirectory add(JobEventRenamedOneDirectory p) {
        return jobEventRenamedOneDirectoryDao.save(p);
    }
}
