package ru.testfield.training.userService.services.mock;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.testfield.training.userService.models.UploadedFile;

import java.util.UUID;

/**
 * Created by aNNufriy in Jan, 2019
 */
public interface UploadedFileRepository extends JpaRepository<UploadedFile, UUID> {
}
