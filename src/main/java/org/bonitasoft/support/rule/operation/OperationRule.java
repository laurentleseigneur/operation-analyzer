package org.bonitasoft.support.rule.operation;

import org.bonitasoft.engine.bpm.flownode.ActivityDefinition;
import org.bonitasoft.engine.bpm.process.ProcessDeploymentInfo;
import org.bonitasoft.engine.operation.Operation;
import org.bonitasoft.support.reports.Reporter;
import org.bonitasoft.support.rule.Rule;

/**
 * @author Laurent Leseigneur
 */
public interface OperationRule extends Rule {

    void executeOperationCheck(Reporter reporter, ProcessDeploymentInfo processDeploymentInfo, ActivityDefinition activity, Operation operation);


}
