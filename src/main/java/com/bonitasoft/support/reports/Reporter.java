package com.bonitasoft.support.reports;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Laurent Leseigneur
 */
public class Reporter {

    private final List<ProcessReportWarning> processReportWarnings;

    private final List<ActivityReportWarning> activityReportWarnings;

    public Reporter() {
        this.processReportWarnings = new ArrayList<>();
        this.activityReportWarnings = new ArrayList<>();
    }

    public void reportFailingRule(ProcessReportWarning processReportWarning) {
        this.processReportWarnings.add(processReportWarning);
    }

    public void reportFailingRule(ActivityReportWarning activityReportWarning) {
        this.activityReportWarnings.add(activityReportWarning);
    }

    public List<ActivityReportWarning> getActivityReportWarnings() {
        return activityReportWarnings;
    }

    public List<ProcessReportWarning> getProcessReportWarnings() {
        return processReportWarnings;
    }
}
