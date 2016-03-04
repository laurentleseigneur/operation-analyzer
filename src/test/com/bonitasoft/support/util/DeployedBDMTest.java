package com.bonitasoft.support.util;

import java.io.File;

import org.assertj.core.api.Assertions;
import org.bonitasoft.engine.session.APISession;
import org.junit.Test;

import com.bonitasoft.engine.api.LoginAPI;
import com.bonitasoft.engine.api.TenantAPIAccessor;

/**
 * @author Laurent Leseigneur
 */
public class DeployedBDMTest {

    private LoginAPI loginAPI;
    private APISession apiSession;

    @Test
    public void deployedBdmTest() throws Exception {
        //given
        System.setProperty("bonita.home", new File("bonita-home").getAbsolutePath());
        loginAPI = TenantAPIAccessor.getLoginAPI();
        apiSession = loginAPI.login("install", "install");

        //when
        DeployedBDM deployedBDM =new DeployedBDM(apiSession);
        loginAPI.logout(apiSession);
        //then
        Assertions.assertThat( deployedBDM.getDeployedClassNames()).isNotEmpty();

    }

}
