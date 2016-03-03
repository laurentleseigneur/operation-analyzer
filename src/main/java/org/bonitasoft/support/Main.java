package org.bonitasoft.support;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

/**
 * @author Laurent Leseigneur
 */
public class Main {

    public static void main(String[] args) {

        final OperationAnalyserRunner operationAnalyserRunner = new OperationAnalyserRunner();
        CmdLineParser cmdLineParser = new CmdLineParser(operationAnalyserRunner);
        try {
            cmdLineParser.parseArgument(args);
            operationAnalyserRunner.run();
        } catch (CmdLineException e) {
            System.err.println("error:"+e.getMessage());
            System.err.println("parameters:");
            cmdLineParser.printUsage(System.err);
        }

    }

}
