package ru.testfield.training.userService.services;

import org.springframework.web.multipart.MultipartFile;
import ru.testfield.training.userService.models.User;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Created by aNNufriy in Jan, 2019
 */
public interface RemoteService {
    String sendPicture(MultipartFile multipartFile);

    UUID sendUserInfo(User user);

    User getUserInfo(UUID userId);

    Map<String,Object> setUserStatus(UUID userId, Boolean newStatus);

    Set<Map<String,Object>> getServerStatistics(Boolean online, Long id);
}