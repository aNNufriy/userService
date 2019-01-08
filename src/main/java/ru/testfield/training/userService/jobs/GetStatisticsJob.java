package ru.testfield.training.userService.jobs;

import java.util.Map;
import java.util.Set;

/**
 * Created by aNNufriy in Jan, 2019
 */
public class GetStatisticsJob extends AbstractJob<Set<Map<String, Object>>>
{
    final private Boolean status;
    final private Long timestamp;

    public GetStatisticsJob(Boolean status, Long timestamp)
    {
        this.status = status;
        this.timestamp = timestamp;
        this.jobPriority = JobPriority.HIGH;
    }

    @Override
    public Set<Map<String, Object>> sendRequest()
    {
        return remoteService.getServerStatistics(status, timestamp);
    }

}
