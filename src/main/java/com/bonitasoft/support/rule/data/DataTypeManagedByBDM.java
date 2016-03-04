package com.bonitasoft.support.rule.data;

import static java.lang.String.format;

import com.bonitasoft.support.reports.ProcessReportWarning;
import com.bonitasoft.support.util.DeployedBDM;
import org.bonitasoft.engine.bpm.data.DataDefinition;
import org.bonitasoft.engine.bpm.flownode.ActivityDefinition;
import org.bonitasoft.engine.bpm.process.ProcessDeploymentInfo;
import com.bonitasoft.support.reports.ActivityReportWarning;
import com.bonitasoft.support.reports.Reporter;
import com.bonitasoft.support.rule.AbstractRule;

/**
 * @author Laurent Leseigneur
 */
public class DataTypeManagedByBDM extends AbstractRule implements DataTypeRule {

    private final DeployedBDM deployedBDM;

    public DataTypeManagedByBDM(DeployedBDM deployedBDM) {
        this.deployedBDM = deployedBDM;
    }

    @Override
    public void executeActivityCheck(Reporter reporter, ProcessDeploymentInfo processDeploymentInfo, ActivityDefinition activity,
            DataDefinition dataDefinition) {
        final String className = dataDefinition.getClassName();
        if (isDataDefinitionTypeManagedByBDM(className)) {
            final String message = format("activity variable '%s' is defined with type '%s' which is managed by BDM", dataDefinition.getName(), className);
            reporter.reportFailingRule(new ActivityReportWarning(processDeploymentInfo, activity,
                    message));
        }
    }

    @Override
    public void executeProcessCheck(Reporter reporter, ProcessDeploymentInfo processDeploymentInfo, DataDefinition dataDefinition) {
        final String className = dataDefinition.getClassName();
        if (isDataDefinitionTypeManagedByBDM(className)) {
            final String message = format("process variable '%s' is defined with type '%s' which is managed by BDM", dataDefinition.getName(), className);
            reporter.reportFailingRule(new ProcessReportWarning(processDeploymentInfo,
                    message));
        }
    }

    private boolean isDataDefinitionTypeManagedByBDM(String className) {
        return deployedBDM.getDeployedClassNames().contains(className);
    }

}
