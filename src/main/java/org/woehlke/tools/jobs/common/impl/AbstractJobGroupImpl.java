package org.woehlke.tools.jobs.common.impl;

import org.woehlke.tools.jobs.common.AbstractJobGroup;

public abstract class AbstractJobGroupImpl extends Thread implements AbstractJobGroup {

    public abstract void run();
}
