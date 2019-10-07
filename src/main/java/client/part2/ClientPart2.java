package client.part2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import client.part1.ClientPart1;
import client.part1.Phase;
import client.part1.RequestRecord;

public class ClientPart2 extends ClientPart1 {

    public ClientPart2(int numThreads, int numRuns, int numSkiers, int numLifts, String basePath) {
        super(numThreads, numRuns, numSkiers, numLifts, basePath, true);
    }

    @Override
    public void output() {
        super.output();
        // Report stats
        Phase phase1 = getPhase1();
        Phase phase2 = getPhase2();
        Phase phase3 = getPhase3();
        List<RequestRecord> records = new ArrayList<>();
        records.addAll(phase1.getRecords().values());
        records.addAll(phase2.getRecords().values());
        records.addAll(phase3.getRecords().values());
        writeCsv(records);
        ArrayList<Long> responseTimeList = getResponseTimes(records);
        System.out.println("Statistics:");
        System.out.println(String.format("\tMean response time(ms): %f", getMeanResponseTime(responseTimeList)));
        System.out.println(String.format("\tMedian response time(ms): %d", getMedianResponseTime(responseTimeList)));
        System.out.println(String.format("\tThroughput(requests/ms): %f",
            (phase1.getTotalRequests() + phase2.getTotalRequests() + phase3.getTotalRequests())
                * 1.0 / (getWallTime())));
        System.out.println(String.format("\tP99 response time(ms): %d", getP99ResponseTime(responseTimeList)));
        System.out.println(String.format("\tMax response time(ms): %d", getMaxResponseTime(responseTimeList)));
    }

    /* Stats helper */
    private double getMeanResponseTime(List<Long> responseTimes) {
        return responseTimes.stream().mapToLong(val -> val).average().getAsDouble();
    }

    private long getMedianResponseTime(List<Long> responseTimes) {
        long median;
        if (responseTimes.size() % 2 == 0)
            median = (responseTimes.get(responseTimes.size()/2) + responseTimes.get(responseTimes.size()/2 - 1))/2;
        else
            median = responseTimes.get(responseTimes.size()/2);

        return median;
    }

    private long getP99ResponseTime(List<Long> responseTimes) {
        return responseTimes.get(responseTimes.size()*99/100);
    }

    private Long getMaxResponseTime(List<Long> responseTimes) {
        return responseTimes.get(responseTimes.size()-1);
    }

    private ArrayList<Long> getResponseTimes(List<RequestRecord> records) {
        ArrayList<Long> res = new ArrayList<>();
        for(RequestRecord record : records) {
            res.add(record.getLatency());
        }
        Collections.sort(res);

        return res;
    }

    /* Write csv helper */
    private void writeCsv(List<RequestRecord> records) {
        List<String> recordList = new ArrayList<>();
        String filename = String.format("%s_%d_threads_records.csv", this.getClass().getName(),
            getNumThreads());
        recordList.add("start time,request type,latency,response code");
        for(RequestRecord sr : records) {
            recordList.add(sr.toString());
        }
        try {
            Files.write(Paths.get(filename), recordList);
        } catch (IOException e) {
            System.out.println("failed write file");
            e.printStackTrace();
        }
    }
}
