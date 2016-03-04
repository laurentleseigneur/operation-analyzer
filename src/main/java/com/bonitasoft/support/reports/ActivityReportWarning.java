package com.bonitasoft.support.reports;

import org.bonitasoft.engine.bpm.flownode.ActivityDefinition;
import org.bonitasoft.engine.bpm.process.ProcessDeploymentInfo;

/**
 * @author Laurent Leseigneur
 */
public class ActivityReportWarning extends ProcessReportWarning {

    private final String activityName;

    public ActivityReportWarning(ProcessDeploymentInfo processDeploymentInfo, ActivityDefinition activity, String message) {
        super(processDeploymentInfo, message);
        this.activityName = activity.getName();

    }

    public String getActivityName() {
        return activityName;
    }
}
