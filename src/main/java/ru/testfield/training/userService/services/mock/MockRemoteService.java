package ru.testfield.training.userService.services.mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.testfield.training.userService.models.UploadedFile;
import ru.testfield.training.userService.models.User;
import ru.testfield.training.userService.services.RemoteService;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by aNNufriy in Jan, 2019
 */
@Service
public class MockRemoteService implements RemoteService {

    final UserRepository userRepository;

    final StorageService storageService;

    @Value("upload.dir")
    private String UPLOAD_DIR;

    @Autowired
    public MockRemoteService(UserRepository userRepository, StorageService storageService) {
        this.userRepository = userRepository;
        this.storageService = storageService;
    }

    @Override
    public String sendPicture(MultipartFile multipartFile) {
        UploadedFile uploadedFile;
        try {
            uploadedFile = storageService.store(multipartFile);
        } catch (NoSuchAlgorithmException | IOException e) {
            return "";
        }
        return uploadedFile.getPath();
    }

    @Override
    public UUID sendUserInfo(User user) {
        userRepository.save(user);
        return user.getId();
    }

    @Override
    public User getUserInfo(UUID userId) {
        return userRepository.findById(userId).get();
    }

    @Override
    public Map<String,Object> setUserStatus(UUID userId, Boolean newStatus) {
        User user = userRepository.findById(userId).get();

        Boolean statusBeforeChange = user.getStatusOnline();

        user.setStatusOnline(newStatus);
        user.setStatusUpdateTime(System.currentTimeMillis()/1000);
        userRepository.save(user);

        Map<String,Object> result = new HashMap<>();
        result.put("id",user.getId());
        result.put("newStatus",newStatus);
        if(statusBeforeChange!=null) {
            result.put("oldStatus", statusBeforeChange);
        }

        return result;
    }

    @Override
    public Set<Map<String,Object>> getServerStatistics(Boolean statusToBeFound, Long timestamp) {
        Collection<User> filteredUsers;
        if(statusToBeFound==null && timestamp==null) {
            filteredUsers = userRepository.findAll();
        }else{
            if (timestamp == null) {
                timestamp = 0L;
            }

            if(statusToBeFound!=null) {
                filteredUsers = userRepository.findByStatusOnlineAndStatusUpdateTimeAfter(statusToBeFound, timestamp);
            }else{
                filteredUsers = userRepository.findByStatusUpdateTimeAfter(timestamp);
            }
        }

        Set<Map<String, Object>> userStatistics = filteredUsers
                .stream()
                .map(a -> {
                    Map<String, Object> user = new HashMap<>();
                    user.put("id", a.getId());
                    user.put("picture", a.getPicture());
                    if (a.getStatusOnline() != null) {
                        user.put("status", a.getStatusOnline());
                    }
                    if(a.getStatusUpdateTime()!=null) {
                        user.put("update_time", a.getStatusUpdateTime());
                    }
                    return user;
                })
                .collect(Collectors.toSet());

        return userStatistics;
    }
}