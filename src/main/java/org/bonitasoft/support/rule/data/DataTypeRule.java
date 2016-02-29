package org.bonitasoft.support.rule.data;

import org.bonitasoft.engine.bpm.data.DataDefinition;
import org.bonitasoft.engine.bpm.flownode.ActivityDefinition;
import org.bonitasoft.engine.bpm.process.ProcessDeploymentInfo;
import org.bonitasoft.support.reports.Reporter;
import org.bonitasoft.support.rule.Rule;

/**
 * @author Laurent Leseigneur
 */
public interface DataTypeRule extends Rule {


    void executeActivityCheck(Reporter reporter, ProcessDeploymentInfo processDeploymentInfo, ActivityDefinition activity, DataDefinition dataDefinition);

    void executeProcessCheck(Reporter reporter, ProcessDeploymentInfo processDeploymentInfo, DataDefinition dataDefinition);
}
