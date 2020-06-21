package org.woehlke.tools.model.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.tools.model.dao.ToolsLogbuchDao;
import org.woehlke.tools.model.entities.ToolsLogbuch;
import org.woehlke.tools.model.common.impl.JobEventServiceImpl;
import org.woehlke.tools.model.services.ToolsLogbuchService;

@Service
public class ToolsLogbuchServiceImpl extends JobEventServiceImpl<ToolsLogbuch> implements ToolsLogbuchService {

    @Autowired
    public ToolsLogbuchServiceImpl(ToolsLogbuchDao toolsLogbuchDao) {
        super(toolsLogbuchDao);
    }

}
