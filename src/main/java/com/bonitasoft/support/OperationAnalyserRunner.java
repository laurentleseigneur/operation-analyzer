package com.bonitasoft.support;

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
import org.kohsuke.args4j.Option;

/**
 * @author Laurent Leseigneur
 */
public class OperationAnalyserRunner {

    @Option(name = "-bonitaHome", usage = "path to bonita home folder", required = true)
    public File bonitaHome;

    @Option(name = "-user", usage = "tenant admin login", required = true)
    public String tenantAdminLogin;

    @Option(name = "-password", usage = "tenant admin password", required = true)
    public String tenantAdminPassword;


    @Option(name = "-outputFile", usage = "optional. path to json report file (override existing file)", required = false)
    public File reportFile;


    @Option(name = "-outputConsole", usage = "optional. display report to console", required = false)
    public boolean outputConsole;

    public void run() {
        //setup
        try {
            System.setProperty("bonita.home", bonitaHome.getAbsolutePath());
            Reporter reporter = new Reporter();
            LoginAPI loginAPI = null;
            loginAPI = TenantAPIAccessor.getLoginAPI();
            final APISession apiSession = loginAPI.login(tenantAdminLogin, tenantAdminPassword);
            DeployedBDM deployedBDM = new DeployedBDM(apiSession);
            List<OperationRule> operationRules = getOperationRules();
            List<DataTypeRule> dataTypeRules = getDataTypeRules(deployedBDM);

            JsonReportBuilder jsonReportBuilder = new JsonReportBuilder(reporter);

            //run
            OperationAnalyzer operationAnalyzer = new OperationAnalyzer(apiSession, reporter, operationRules, dataTypeRules, deployedBDM);
            operationAnalyzer.analyzeTenant();
            loginAPI.logout(apiSession);

            if (reportFile != null) {
                jsonReportBuilder.getJsonFileReport(reportFile);
                System.out.println("write report to file:"+reportFile.getAbsolutePath());
            }

            if (outputConsole){
                System.out.println(jsonReportBuilder.getJsonReport());
            }


        } catch (Exception e) {
            System.err.println("error:" + e.getMessage());
            e.printStackTrace(System.err);
        }


    }

    public List<DataTypeRule> getDataTypeRules(DeployedBDM deployedBDM) {
        List<DataTypeRule> dataTypeRules = new ArrayList<>();
        dataTypeRules.add(new DataTypeManagedByBDM(deployedBDM));
        dataTypeRules.add(new DataTypeCollectionInitByBDMQuery(deployedBDM));
        return dataTypeRules;
    }

    public List<OperationRule> getOperationRules() {
        List<OperationRule> operationRules = new ArrayList<>();
        operationRules.add(new DataAssignmentByBORule());
        operationRules.add(new DataAssignmentByBDMQueryRule());
        return operationRules;
    }
}
