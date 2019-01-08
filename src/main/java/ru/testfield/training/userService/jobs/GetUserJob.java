package ru.testfield.training.userService.jobs;

import ru.testfield.training.userService.models.User;

import java.util.UUID;

/**
 * Created by aNNufriy in Jan, 2019
 */
public class GetUserJob extends AbstractJob<User>
{
    final private UUID userId;

    public GetUserJob(UUID userId)
    {
        this.jobPriority = JobPriority.HIGH;
        this.userId = userId;
    }

    @Override
    public User sendRequest()
    {
        return remoteService.getUserInfo(userId);
    }
}
