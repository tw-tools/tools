package org.woehlke.tools.jobs.common.impl;

import org.woehlke.tools.jobs.common.AbstractJobGroupService;

public abstract class AbstractJobGroupServiceImpl extends Thread implements AbstractJobGroupService {

    public abstract void run();
}
