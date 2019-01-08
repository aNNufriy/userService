package ru.testfield.training.userService.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.testfield.training.userService.services.RemoteService;

import java.util.concurrent.Callable;

/**
 * Created by aNNufriy in Jan, 2019
 */
public abstract class AbstractJob<V> implements Callable {

    private static final long WORK_EMULATION_TIMEOUT = 0;

    private static final Logger logger = LoggerFactory.getLogger(AbstractJob.class);

    protected RemoteService remoteService = null;

    protected JobPriority jobPriority = JobPriority.LOW;

    @Override
    public V call()
    {
        try {
            logger.debug("Durable processing emulation...");
            Thread.sleep(WORK_EMULATION_TIMEOUT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return sendRequest();
    }

    public void setRemoteService(RemoteService remoteService)
    {
        this.remoteService = remoteService;
    }

    public abstract V sendRequest();

    public JobPriority getJobPriority()
    {
        return jobPriority;
    }

    public enum JobPriority
    {
        HIGH,
        LOW
    }
}