package com.bonitasoft.support;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.bonitasoft.engine.api.LoginAPI;
import com.bonitasoft.engine.api.TenantAPIAccessor;
import com.bonitasoft.support.reports.Reporter;
import com.bonitasoft.support.rule.data.DataTypeCollectionInitByBDMQuery;
import com.bonitasoft.support.rule.data.DataTypeManagedByBDM;
import com.bonitasoft.support.rule.data.DataTypeRule;
import com.bonitasoft.support.rule.operation.DataAssignmentByBDMQueryRule;
import com.bonitasoft.support.rule.operation.DataAssignmentByBORule;
import com.bonitasoft.support.rule.operation.OperationRule;
import com.bonitasoft.support.util.DeployedBDM;
import org.bonitasoft.engine.session.APISession;
import com.bonitasoft.support.util.JsonReportBuilder;
import org.junit.Test;

/**
 * @author Laurent Leseigneur
 */
public class OperationAnalyzerTest {

    private LoginAPI loginAPI;
    private APISession apiSession;



    @Test
    public void testAnalyze() throws Exception {
        //setup
        System.setProperty("bonita.home", new File("bonita-home").getAbsolutePath());
        Reporter reporter = new Reporter();
        loginAPI = TenantAPIAccessor.getLoginAPI();
        apiSession = loginAPI.login( "install", "install");


        DeployedBDM deployedBDM =new DeployedBDM(apiSession);
        List<OperationRule> operationRules = getOperationRules();
        List<DataTypeRule> dataTypeRules = getDataTypeRules(deployedBDM);

        //run
        OperationAnalyzer operationAnalyzer = new OperationAnalyzer(apiSession, reporter, operationRules, dataTypeRules, deployedBDM);
        operationAnalyzer.analyzeTenant();
        loginAPI.logout(apiSession);

        //get report
        JsonReportBuilder jsonReportBuilder = new JsonReportBuilder(reporter);
        System.err.println(jsonReportBuilder.getJsonReport());

        File jsonReport = new File("report.json");
        jsonReport.mkdirs();
        if (jsonReport.exists()) {
            jsonReport.delete();
        }
        jsonReportBuilder.getJsonFileReport(jsonReport);
        assertThat(jsonReport).exists();

    }

    private List<DataTypeRule> getDataTypeRules(DeployedBDM deployedBDM) {
        List<DataTypeRule> dataTypeRules = new ArrayList<>();
        dataTypeRules.add(new DataTypeManagedByBDM(deployedBDM));
        dataTypeRules.add(new DataTypeCollectionInitByBDMQuery(deployedBDM));
        return dataTypeRules;
    }

    private List<OperationRule> getOperationRules() {
        List<OperationRule> operationRules = new ArrayList<>();
        operationRules.add(new DataAssignmentByBORule());
        operationRules.add(new DataAssignmentByBDMQueryRule());
        return operationRules;
    }

}
