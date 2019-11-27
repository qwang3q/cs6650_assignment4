package server;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;
import databaseUtils.StatsDao;

public class StatisticOperator implements ServletContextListener {
    // A random key as key in database for identifying the row responsible for current server
    private String uuid;
    // Shared filepath for recording response durations
    private String resortGetStatPath;
    private String resortPostStatPath;
    private String skierGetStatPath;
    private String skierPostStatPath;

    private ScheduledExecutorService scheduler;

    public void contextInitialized(ServletContextEvent e) {
        ServletContext cntxt = e.getServletContext();

        uuid = UUID.randomUUID().toString();
        try {
            resortGetStatPath = File.createTempFile(uuid + "_resort_get", null).getAbsolutePath();
            resortPostStatPath = File.createTempFile(uuid + "_resort_post", null).getAbsolutePath();
            skierGetStatPath = File.createTempFile(uuid + "_skier_get", null).getAbsolutePath();
            skierPostStatPath = File.createTempFile(uuid + "_skier_post", null).getAbsolutePath();
        } catch (IOException ioErr) {
            ioErr.printStackTrace();
            return;
        }

        System.out.println("Stats stored on this server:");
        System.out.println(resortPostStatPath);
        System.out.println(resortPostStatPath);
        System.out.println(skierGetStatPath);
        System.out.println(skierPostStatPath);

        cntxt.setAttribute("resortGetStatPath", resortGetStatPath);
        cntxt.setAttribute("resortPostStatPath", resortPostStatPath);
        cntxt.setAttribute("skierGetStatPath", skierGetStatPath);
        cntxt.setAttribute("skierPostStatPath", skierPostStatPath);

        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new StatsReporter(this.uuid, resortGetStatPath, resortPostStatPath, skierGetStatPath, skierPostStatPath),
            0, 5, TimeUnit.SECONDS);
    }

    private class StatsReporter implements Runnable {
        protected StatsDao statsDao;

        private String uuid;
        private String resortGetStatPath;
        private String resortPostStatPath;
        private String skierGetStatPath;
        private String skierPostStatPath;

        public StatsReporter(
            String uuid,
            String resortGetStatPath,
            String resortPostStatPath,
            String skierGetStatPath,
            String skierPostStatPath)
        {
            this.uuid = uuid;
            this.resortGetStatPath = resortGetStatPath;
            this.resortPostStatPath = resortPostStatPath;
            this.skierGetStatPath = skierGetStatPath;
            this.skierPostStatPath = skierPostStatPath;
        }

        @Override
        public void run() {
            statsDao = StatsDao.getInstance();

            System.out.println("Thread up");
            Stat resortGetStat = new Stat("Resort", "GET", resortGetStatPath);
            Stat resortPostStat = new Stat("Resort", "POST", resortPostStatPath);
            Stat skierGetStat = new Stat("Skier", "GET", skierGetStatPath);
            Stat skierPostStat = new Stat("Skier", "POST", skierPostStatPath);

            for (Stat stat : new Stat[]{
                resortGetStat, resortPostStat, skierGetStat, skierPostStat
            }) {
                stat.loadFromFile();

                // Write uuid, count, mean, max, server, operation in database
                try {
                    statsDao.updateStat(this.uuid, stat);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void contextDestroyed(ServletContextEvent e){
        scheduler.shutdownNow();
        System.out.println("Destroyed");
    }
}
