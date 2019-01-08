package ru.testfield.training.userService.jobs;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by aNNufriy in Jan, 2019
 */
public class AddPhotoJob extends AbstractJob<String> {
    private MultipartFile multipartFile;

    public AddPhotoJob(MultipartFile multipartFile)
    {
        this.multipartFile = multipartFile;
    }

    @Override
    public String sendRequest()
    {
        return remoteService.sendPicture(multipartFile);
    }

}