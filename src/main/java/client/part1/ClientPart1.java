package client.part1;

public class ClientPart1 {
    // maximum number of threads to run (numThreads - max 256)
    private int numThreads;
    // number of skier to generate lift rides for (numSkiers - max 50000), This is effectively the skier's ID (skierID)
    private int numSkiers;
    // number of ski lifts (numLifts - range 5-60, default 40)
    private int numLifts = 40;
    // mean numbers of ski lifts each skier rides each day (numRuns - default 10, max 20)
    private int numRuns = 10;
    // IP/port address of the server
    private String basePath;
    private Phase phase1;
    private Phase phase2;
    private Phase phase3;
    private long wallTime;
    private boolean traceLatency;

    public ClientPart1(int numThreads, int numRuns, int numSkiers, int numLifts, String basePath, boolean traceLatency) {
        this.numThreads = Math.min(numThreads, 256);
        this.numSkiers = Math.min(numSkiers, 50000);
        this.numLifts = Math.min(Math.max(numLifts, 5), 60);
        this.numRuns = Math.min(numRuns, 20);
        this.basePath = basePath;
        this.traceLatency = traceLatency;

        // Define phases
        this.phase1 = new Phase(this.numThreads / 4, this.numRuns / 10,
            this.numSkiers, 0, 90, this.numLifts, basePath, this.traceLatency, false);
        this.phase2 = new Phase(this.numThreads, this.numRuns * 8 / 10,
            this.numSkiers,91, 360, this.numLifts, basePath, this.traceLatency, false);
        this.phase3 = new Phase(this.numThreads / 4, this.numRuns / 10,
            this.numSkiers, 361, 420, this.numLifts, basePath, this.traceLatency, true);
    }

    public ClientPart1(int numThreads, int numRuns, int numSkiers, int numLifts, String basePath) {
        this(numThreads,numRuns,numSkiers,numLifts,basePath, false);
    }

    public Phase getPhase1() {
        return phase1;
    }

    public Phase getPhase2() {
        return phase2;
    }

    public Phase getPhase3() {
        return phase3;
    }

    public long getWallTime() {
        return wallTime;
    }

    public int getNumThreads() {
        return numThreads;
    }

    public void start() {
        System.out.println(String.format("\n\nNumber of threads: %d", this.numThreads));

        // Execute phases
        long startTime = System.currentTimeMillis();
        phase1.start();
        phase1.waitForTenPercent();
        phase2.start();
        phase2.waitForTenPercent();
        phase3.start();
        phase1.waitForCompletion();
        phase2.waitForCompletion();
        phase3.waitForCompletion();
        long endTime = System.currentTimeMillis();
        this.wallTime = endTime - startTime;
    }

    public void output() {
        // Report overall summary
        System.out.println("Summary:");
        System.out.println(String.format("\tRequests sent: %d",
            phase1.getTotalRequests() + phase2.getTotalRequests() + phase3.getTotalRequests()));
        System.out.println(String.format("\tRequests succeeded: %d",
            (phase1.getTotalRequests() + phase2.getTotalRequests() + phase3.getTotalRequests())
                - (phase1.getTotalFailedCount() + phase2.getTotalFailedCount() + phase3.getTotalFailedCount())));
        System.out.println(String.format("\tWall time: %d ms", this.wallTime));
    }

    private void clean() {
        // Exit cleanly
        phase1.workerThreadsPool.shutdown();
        phase2.workerThreadsPool.shutdown();
        phase3.workerThreadsPool.shutdown();
    }

    public void run() {
        start();
        output();
        clean();
    }

}
