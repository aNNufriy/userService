package ru.testfield.training.userService.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.testfield.training.userService.jobs.AbstractJob;

import java.util.concurrent.*;

/**
 * Created by aNNufriy in Jan, 2019
 */
@Service
public class PriorityJobScheduler {

//TODO: priority scheduling
//    private final Integer POOL_SIZE
//            = Runtime.getRuntime().availableProcessors();
//    private final Integer QUEUE_SIZE = 100;
//    private ExecutorService priorityJobPoolExecutor
//            = new ThreadPoolExecutor(POOL_SIZE, POOL_SIZE, 0L, TimeUnit.MILLISECONDS, new PriorityBlockingQueue<>());

    @Autowired
    private RemoteService remoteService = null;

    private ExecutorService priorityJobScheduler
            = Executors.newWorkStealingPool();

    public <T> Future <T> scheduleJob(AbstractJob<T> abstractJob) {
        abstractJob.setRemoteService(remoteService);
        return priorityJobScheduler.submit(abstractJob);
    }
}