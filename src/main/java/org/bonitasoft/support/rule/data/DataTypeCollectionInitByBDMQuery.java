package org.bonitasoft.support.rule.data;

import static java.lang.String.format;

import org.bonitasoft.engine.bpm.data.DataDefinition;
import org.bonitasoft.engine.bpm.flownode.ActivityDefinition;
import org.bonitasoft.engine.bpm.process.ProcessDeploymentInfo;
import org.bonitasoft.engine.expression.Expression;
import org.bonitasoft.support.reports.ActivityReportWarning;
import org.bonitasoft.support.reports.ProcessReportWarning;
import org.bonitasoft.support.reports.Reporter;
import org.bonitasoft.support.rule.AbstractRule;
import org.bonitasoft.support.util.DeployedBDM;

/**
 * @author Laurent Leseigneur
 */
public class DataTypeCollectionInitByBDMQuery extends AbstractRule implements DataTypeRule {

    private final DeployedBDM deployedBDM;

    public DataTypeCollectionInitByBDMQuery(DeployedBDM deployedBDM) {
        this.deployedBDM = deployedBDM;
    }

    @Override
    public void executeActivityCheck(Reporter reporter, ProcessDeploymentInfo processDeploymentInfo, ActivityDefinition activity,
            DataDefinition dataDefinition) {
        final String className = dataDefinition.getClassName();
        if (isDataCollectionWithBdmQueryInit(dataDefinition, className)) {
            final String message = format("activity variable '%s' is defined as multiple using a BDM query as default value", dataDefinition.getName(), className);
            reporter.reportFailingRule(new ActivityReportWarning(processDeploymentInfo, activity,
                    message));
        }
    }

    @Override
    public void executeProcessCheck(Reporter reporter, ProcessDeploymentInfo processDeploymentInfo, DataDefinition dataDefinition) {
        final String className = dataDefinition.getClassName();
        if (isDataCollectionWithBdmQueryInit(dataDefinition, className)) {
            final String message = format("process variable '%s' is defined as multiple using a BDM query as default value", dataDefinition.getName(), className);
            reporter.reportFailingRule(new ProcessReportWarning(processDeploymentInfo,
                    message));
        }
    }

    private boolean isDataCollectionWithBdmQueryInit(DataDefinition dataDefinition, String className) {
        if ("java.util.Collection".equals(className)) {
            final Expression defaultValueExpression = dataDefinition.getDefaultValueExpression();
            return defaultValueExpression != null && "TYPE_QUERY_BUSINESS_DATA".equals(defaultValueExpression.getExpressionType());
        }
        return false;
    }

}
