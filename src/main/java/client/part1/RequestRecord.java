package client.part1;

public class RequestRecord {
    private long startTime;
    private String requestType;
    private long latency;
    private int responseCode;

    public RequestRecord(long startTime, String requestType, long latency, int responseCode) {
        this.startTime = startTime;
        this.requestType = requestType;
        this.latency = latency;
        this.responseCode = responseCode;
    }

    @Override
    public String toString() {
        return String.format("%d,%s,%d,%d", this.startTime, this.requestType, this.latency,
            this.responseCode);
    }

    public long getLatency() {
        return latency;
    }
}