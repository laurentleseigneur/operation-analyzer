package com.bonitasoft.support.util;

import static org.bonitasoft.engine.io.IOUtil.createTempDirectoryInDefaultTempDirectory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bonitasoft.engine.api.TenantAPIAccessor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.bonitasoft.engine.io.IOUtil;
import org.bonitasoft.engine.session.APISession;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.dom4j.XPath;

/**
 * @author Laurent Leseigneur
 */
public class DeployedBDM {

    private final APISession apiSession;
    private final Set<String> deployedClassNames;

    public DeployedBDM(APISession apiSession) {
        this.apiSession = apiSession;
        this.deployedClassNames = new HashSet<>();
        initBdm();
    }

    private void initBdm() {
        try {
            if (isBdmDeployed()) {
                final File bdmZipFile = IOUtil.createTempFileInDefaultTempDirectory("bdmZip","zip") ;
                FileUtils.writeByteArrayToFile(bdmZipFile, TenantAPIAccessor.getTenantManagementAPI(apiSession).getClientBDMZip());
                final File bomXml = unzipBomXmlFile(bdmZipFile);
                final Document document = getDocument(bomXml);
                XPath xpath = document.createXPath("//businessObject");
                List<Node> nodes = xpath.selectNodes(document);
                for (Node node : nodes) {
                    deployedClassNames.add(node.selectSingleNode("@qualifiedName").getStringValue());
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private Document getDocument(File bomXml) throws Exception {
        InputStream inputStream = new FileInputStream(bomXml);
        final byte[] byteArray = IOUtils.toByteArray(inputStream);
        return DocumentHelper.parseText(new String(byteArray));
    }

    private File unzipBomXmlFile(File bdmZipFile) throws IOException {
        final File folder = createTempDirectoryInDefaultTempDirectory("BDM");
        IOUtil.unzipToFolder(new FileInputStream(bdmZipFile), folder);
        File bomZipFile = new File(folder.getAbsolutePath() + File.separator + "bom.zip");
        IOUtil.unzipToFolder(new FileInputStream(bomZipFile), folder);
        return new File(folder.getAbsolutePath() + File.separator + "bom.xml");

    }

    private boolean isBdmDeployed() throws Exception {
        return TenantAPIAccessor.getTenantManagementAPI(apiSession).getBusinessDataModelVersion() != null;
    }

    public Set<String> getDeployedClassNames() {
        return deployedClassNames;
    }
}
