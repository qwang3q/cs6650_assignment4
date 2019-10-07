package client;

import client.part1.ClientPart1;
import client.part2.ClientPart2;

public class Main {
    public static void main(String[] args) {
 //       String basePath = "http://127.0.0.1:8080/WEB_APP";
        String basePath = "http://54.200.193.221:8080/assignment1_war";

        int[] numThreadArray = new int[] {32, 64, 128, 256};
        int numSkiers = 20000;
        int numLifts = 40;
        int numRuns = 20;

        for(int numThread : numThreadArray) {
            ClientPart1 clientPart1 = new ClientPart1(numThread, numRuns, numSkiers, numLifts, basePath);
            clientPart1.run();
        }

        for(int numThread : numThreadArray) {
            ClientPart2 clientPart2 = new ClientPart2(numThread, numRuns, numSkiers, numLifts, basePath);
            clientPart2.run();
        }
    }
}
