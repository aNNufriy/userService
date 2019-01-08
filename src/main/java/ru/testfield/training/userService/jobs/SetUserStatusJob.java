package ru.testfield.training.userService.jobs;

import java.util.Map;
import java.util.UUID;

/**
 * Created by aNNufriy in Jan, 2019
 */
public class SetUserStatusJob extends AbstractJob<Map<String,Object>>
{

    final private UUID userId;

    final private Boolean online;

    public SetUserStatusJob(UUID userId, Boolean online)
    {
        this.jobPriority = JobPriority.LOW;
        this.online = online;
        this.userId = userId;
    }

    @Override
    public Map<String,Object> sendRequest()
    {
        return remoteService.setUserStatus(userId,online);
    }
}
