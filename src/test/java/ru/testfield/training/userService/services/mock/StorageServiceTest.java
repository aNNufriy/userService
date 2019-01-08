package ru.testfield.training.userService.services.mock;

import org.junit.Test;
import org.mockito.Mock;

import java.util.UUID;

import static org.mockito.Mockito.*;


/**
 * Created by J.Bgood in Jan, 2019.
 */
public class StorageServiceTest {

    @Mock
    private UploadedFileRepository uploadedFileRepository;

    @Test
    public void store() {
    }

    @Test
    public void getById() {
        when(uploadedFileRepository.findById(UUID.randomUUID())).thenReturn(null);
    }

    @Test
    public void removeFile() {
    }
}