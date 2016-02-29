package org.bonitasoft.support.rule;

import org.bonitasoft.engine.bpm.flownode.ActivityDefinition;
import org.bonitasoft.engine.bpm.process.ProcessDeploymentInfo;

/**
 * @author Laurent Leseigneur
 */
public abstract class AbstractRule implements Rule {

    private String processName;
    private String processVersion;
    private String description;
    private String activityName;

    @Override
    public String getProcessName() {
        return processName;
    }

    @Override
    public String getProcessVersion() {
        return processVersion;
    }

    @Override
    public void setProcessName(String processName) {
        this.processName = processName;
    }

    @Override
    public void setProcessVersion(String processVersion) {
        this.processVersion = processVersion;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    @Override
    public String getActivityName() {
        return activityName;
    }

    protected void setActivityInfo(ActivityDefinition activity) {
        setActivityName(activity.getName());
    }

    protected void setProcessInfo(ProcessDeploymentInfo processDeploymentInfo) {
        setProcessName(processDeploymentInfo.getName());
        setProcessVersion(processDeploymentInfo.getVersion());
    }
}
