package org.bonitasoft.support.rule;

/**
 * @author Laurent Leseigneur
 */
public interface Rule {

    void setDescription(String description);

    String getDescription();

    void setProcessName(String processName);

    String getProcessName();

    void setProcessVersion(String processVersion);

    String getProcessVersion();

    void setActivityName(String activityName);

    String getActivityName();

}
