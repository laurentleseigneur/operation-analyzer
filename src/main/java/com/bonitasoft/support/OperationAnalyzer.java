package com.bonitasoft.support;

import java.util.List;

import com.bonitasoft.engine.api.ProcessAPI;
import com.bonitasoft.engine.api.TenantAPIAccessor;
import com.bonitasoft.support.reports.Reporter;
import com.bonitasoft.support.rule.data.DataTypeRule;
import com.bonitasoft.support.rule.operation.OperationRule;
import com.bonitasoft.support.util.DeployedBDM;
import org.bonitasoft.engine.bpm.data.DataDefinition;
import org.bonitasoft.engine.bpm.flownode.ActivityDefinition;
import org.bonitasoft.engine.bpm.flownode.FlowElementContainerDefinition;
import org.bonitasoft.engine.bpm.process.DesignProcessDefinition;
import org.bonitasoft.engine.bpm.process.ProcessDefinition;
import org.bonitasoft.engine.bpm.process.ProcessDeploymentInfo;
import org.bonitasoft.engine.bpm.process.ProcessDeploymentInfoSearchDescriptor;
import org.bonitasoft.engine.operation.Operation;
import org.bonitasoft.engine.search.Order;
import org.bonitasoft.engine.search.SearchOptions;
import org.bonitasoft.engine.search.SearchOptionsBuilder;
import org.bonitasoft.engine.search.SearchResult;
import org.bonitasoft.engine.session.APISession;

/**
 * @author Laurent Leseigneur
 */
public class OperationAnalyzer {

    private static final int MAX_RESULTS = 100;
    private final APISession apiSession;
    private final Reporter reporter;
    private final List<OperationRule> operationRules;
    private final List<DataTypeRule> dataTypeRules;
    private final DeployedBDM deployedBDM;

    private ProcessAPI processAPI;


    public OperationAnalyzer(APISession apiSession, Reporter reporter, List<OperationRule> operationRules, List<DataTypeRule> dataTypeRules, DeployedBDM deployedBDM) {
        this.apiSession = apiSession;
        this.reporter = reporter;
        this.operationRules = operationRules;
        this.dataTypeRules = dataTypeRules;
        this.deployedBDM = deployedBDM;
    }

    public void analyzeTenant() throws Exception {
        processAPI = TenantAPIAccessor.getProcessAPI(apiSession);

        boolean iterate = true;
        int startIndex = 0;
        while (iterate) {
            final SearchOptions searchOptions = new SearchOptionsBuilder(startIndex, MAX_RESULTS)
                    .sort(ProcessDeploymentInfoSearchDescriptor.DEPLOYMENT_DATE, Order.DESC).done();
            final SearchResult<ProcessDeploymentInfo> deploymentInfoResults = processAPI.searchProcessDeploymentInfos(searchOptions);

            final List<ProcessDeploymentInfo> result = deploymentInfoResults.getResult();
            final long count = (long) result.size();
            for (ProcessDeploymentInfo processDeploymentInfo : result) {
                analyzeProcess(processDeploymentInfo);
            }
            iterate = count > 0;
            startIndex = startIndex + MAX_RESULTS;
        }
    }

    private void analyzeProcess(ProcessDeploymentInfo processDeploymentInfo)
            throws Exception {
        final ProcessDefinition processDefinition = processAPI.getProcessDefinition(processDeploymentInfo.getProcessId());
        final DesignProcessDefinition designProcessDefinition = processAPI.getDesignProcessDefinition(processDefinition.getId());

        final FlowElementContainerDefinition flowElementContainer = designProcessDefinition.getProcessContainer();

        analyzeProcessDataDefinitions(processDeploymentInfo, flowElementContainer);
        analyzeProcessActivities(processDeploymentInfo, flowElementContainer);

    }

    private void analyzeProcessActivities(ProcessDeploymentInfo processDeploymentInfo, FlowElementContainerDefinition flowElementContainer) throws Exception {
        final List<ActivityDefinition> activities = flowElementContainer.getActivities();
        for (ActivityDefinition activity : activities) {
            analyzeActivity(processDeploymentInfo, activity);
        }
    }

    private void analyzeProcessDataDefinitions(ProcessDeploymentInfo processDeploymentInfo, FlowElementContainerDefinition flowElementContainer)
            throws Exception {
        for (DataDefinition dataDefinition : flowElementContainer.getDataDefinitions()) {
            for (DataTypeRule dataTypeRule : dataTypeRules) {
                dataTypeRule.setProcessName(processDeploymentInfo.getName());
                dataTypeRule.setProcessVersion(processDeploymentInfo.getVersion());
                dataTypeRule.executeProcessCheck(reporter, processDeploymentInfo, dataDefinition);
            }
        }
    }

    private void analyzeActivity(ProcessDeploymentInfo processDeploymentInfo, ActivityDefinition activity) throws Exception {
        analyzeActivityDataDefinition(processDeploymentInfo, activity);
        analyzeActivityOperations(processDeploymentInfo, activity);

    }

    private void analyzeActivityDataDefinition(ProcessDeploymentInfo processDeploymentInfo, ActivityDefinition activity) {
        final List<DataDefinition> dataDefinitions = activity.getDataDefinitions();
        for (DataDefinition dataDefinition : dataDefinitions) {
            for (DataTypeRule dataTypeRule : dataTypeRules) {
                dataTypeRule.executeActivityCheck(reporter, processDeploymentInfo, activity, dataDefinition);
            }
        }
    }

    private void analyzeActivityOperations(ProcessDeploymentInfo processDeploymentInfo, ActivityDefinition activity) {
        final List<Operation> operations = activity.getOperations();
        for (Operation operation : operations) {
            analyzeOperation(processDeploymentInfo, activity, operation);
        }
    }

    private void analyzeOperation(ProcessDeploymentInfo processDeploymentInfo, ActivityDefinition activity, Operation operation) {
        for (OperationRule rule : operationRules) {
            rule.executeOperationCheck(reporter, processDeploymentInfo, activity, operation);
        }

    }

}
