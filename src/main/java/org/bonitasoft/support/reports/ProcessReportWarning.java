package org.bonitasoft.support.reports;

import java.io.Serializable;

import org.bonitasoft.engine.bpm.process.ProcessDeploymentInfo;

/**
 * @author Laurent Leseigneur
 */
public class ProcessReportWarning implements Serializable {

    private final String processName;
    private final String processVersion;
    private final String message;
    private final String processDisplayName;

    public ProcessReportWarning(ProcessDeploymentInfo processDeploymentInfo, String message) {
        this.processName = processDeploymentInfo.getName();
        this.processVersion = processDeploymentInfo.getVersion();
        this.processDisplayName = processDeploymentInfo.getDisplayName();
        this.message = message;

    }

    public String getMessage() {
        return message;
    }

    public String getProcessName() {
        return processName;
    }

    public String getProcessVersion() {
        return processVersion;
    }

    public String getProcessDisplayName() {
        return processDisplayName;
    }
}
