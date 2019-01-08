package ru.testfield.training.userService.jobs;

import ru.testfield.training.userService.models.User;

import java.util.UUID;

/**
 * Created by aNNufriy in Jan, 2019
 */
public class AddUserJob extends AbstractJob<UUID>
{

    private User user;

    public AddUserJob(User user)
    {
        this.user = user;
        this.jobPriority = JobPriority.LOW;
    }

    @Override
    public UUID sendRequest()
    {
        return remoteService.sendUserInfo(user);
    }
}
