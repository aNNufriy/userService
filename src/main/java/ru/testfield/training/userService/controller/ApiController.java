package ru.testfield.training.userService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.testfield.training.userService.jobs.*;
import ru.testfield.training.userService.models.User;
import ru.testfield.training.userService.services.PriorityJobScheduler;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by aNNufriy in Jan, 2019
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    private final PriorityJobScheduler priorityJobScheduler;

    @Autowired
    public ApiController(PriorityJobScheduler priorityJobScheduler)
    {
        this.priorityJobScheduler = priorityJobScheduler;
    }

    @PostMapping("/picture/upload")
    public Map<String,String> uploadPicture(MultipartFile file) throws ExecutionException, InterruptedException
    {
        Future<String> future = priorityJobScheduler.scheduleJob(new AddPhotoJob(file));

        Map<String,String> result = new HashMap<>();
        result.put("uri",future.get());
        return result;
    }

    @PostMapping("/user/add")
    public Map<String, UUID> addUser(User user) throws ExecutionException, InterruptedException
    {
        Future<UUID> future = priorityJobScheduler.scheduleJob(new AddUserJob(user));

        Map<String,UUID> result = new HashMap<>();
        result.put("id",future.get());
        return result;
    }

    @GetMapping("/user/{userId}")
    public User getUserInfo(@PathVariable UUID userId) throws ExecutionException, InterruptedException
    {
        Future<User> future = priorityJobScheduler.scheduleJob(new GetUserJob(userId));
        return future.get();
    }

    @GetMapping("/user/{userId}/status/{online}")
    public Map<String,Object> setUserStatus(@PathVariable UUID userId, @PathVariable Boolean online)
            throws ExecutionException, InterruptedException
    {
        Future<Map<String,Object>> changedStatusFuture
                = priorityJobScheduler.scheduleJob(new SetUserStatusJob(userId, online));

        return changedStatusFuture.get();
    }

    @GetMapping("/statistics")
    public Set<Map<String, Object>> getServerStatistics(
            @RequestParam(required = false) Boolean status,
            @RequestParam(required = false) Long timestamp)
            throws ExecutionException, InterruptedException
    {
        GetStatisticsJob getStatisticsJob = new GetStatisticsJob(status, timestamp);
        Future<Set<Map<String, Object>>> setFuture = priorityJobScheduler.scheduleJob(getStatisticsJob);

        return setFuture.get();
    }

}
