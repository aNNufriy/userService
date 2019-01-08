package ru.testfield.training.userService.services.mock;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.testfield.training.userService.models.User;

import java.util.Set;
import java.util.UUID;

/**
 * Created by aNNufriy in Jan, 2019
 */
public interface UserRepository extends JpaRepository<User, UUID> {
    Set<User> findByStatusOnlineAndStatusUpdateTimeAfter(Boolean status, Long timestamp);
    Set<User> findByStatusUpdateTimeAfter(Long timestamp);
}