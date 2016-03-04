package com.bonitasoft.support.rule.operation;

import com.bonitasoft.support.reports.ActivityReportWarning;
import com.bonitasoft.support.reports.Reporter;
import org.bonitasoft.engine.bpm.flownode.ActivityDefinition;
import org.bonitasoft.engine.bpm.process.ProcessDeploymentInfo;
import org.bonitasoft.engine.expression.ExpressionType;
import org.bonitasoft.engine.operation.LeftOperand;
import org.bonitasoft.engine.operation.Operation;
import com.bonitasoft.support.rule.AbstractRule;

/**
 * @author Laurent Leseigneur
 */
public class DataAssignmentByBDMQueryRule extends AbstractRule implements OperationRule {

    @Override
    public void executeOperationCheck(Reporter reporter, ProcessDeploymentInfo processDeploymentInfo, ActivityDefinition activity, Operation operation) {
        if (LeftOperand.TYPE_DATA.equals(operation.getLeftOperand().getType())
                && ExpressionType.TYPE_QUERY_BUSINESS_DATA.name().equals(operation.getRightOperand().getExpressionType())) {
            final String message = String.format("operation assigns variable with name '%s' using a BDM query", operation.getLeftOperand().getName());
            reporter.reportFailingRule(new ActivityReportWarning(processDeploymentInfo, activity, message));
        }

    }

}
