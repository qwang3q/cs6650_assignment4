package client;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import client.part1.ClientPart1;
import client.part2.ClientPart2;

public class Main {
    public static void main(String[] args) {
        // creates pattern layout
        PatternLayout layout = new PatternLayout();
        String conversionPattern = "%-7p %d [%t] %c %x - %m%n";
        layout.setConversionPattern(conversionPattern);

        // creates console appender
        ConsoleAppender consoleAppender = new ConsoleAppender();
        consoleAppender.setLayout(layout);
        consoleAppender.activateOptions();

        // configures the root logger
        Logger rootLogger = Logger.getRootLogger();
        rootLogger.setLevel(Level.DEBUG);
        rootLogger.addAppender(consoleAppender);

        // Local host
//        String basePath = "http://127.0.0.1:8080/WEB_APP";
        // Single EC2 server.
//        String basePath = "http://34.219.254.7:8080/assignment2_war";

        // Single EC2 server cs6650-2.
//        String basePath = "http://54.70.82.246:8080/assignment2_war";

        // Load balancer
//        String basePath = "http://test-balancer-1252926253.us-west-2.elb.amazonaws.com:8080/assignment2_war";
//        String basePath = "http://test567-2103843735.us-west-2.elb.amazonaws.com:8080/assignment2_war";

        String basePath = "http://ski-478715600.us-west-2.elb.amazonaws.com:80/assignment2_war";


//        int[] numThreadArray = new int[] {32, 64, 128, 256};
        int[] numThreadArray = new int[] {256};
        int numSkiers = 20000;
        int numLifts = 40;
        int numRuns = 20;

//        for(int numThread : numThreadArray) {
//            ClientPart1 clientPart1 = new ClientPart1(numThread, numRuns, numSkiers, numLifts, basePath);
//            clientPart1.run();
//        }

        for(int numThread : numThreadArray) {
            ClientPart2 clientPart2 = new ClientPart2(numThread, numRuns, numSkiers, numLifts, basePath);
            clientPart2.run();
        }
    }
}
