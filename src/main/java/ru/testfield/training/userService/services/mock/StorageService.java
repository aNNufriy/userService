package ru.testfield.training.userService.services.mock;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.testfield.training.userService.models.UploadedFile;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Service to manipulate uploaded files and corresponding metadata
 *
 * Created by aNNufriy in Jan, 2019
 */
@Service
public class StorageService {

    @Value("${upload.dir}")
    private String fileStoragePath;

    @Autowired
    private UploadedFileRepository uploadedFileRepository;

    protected final Log logger = LogFactory.getLog(getClass());

    public UploadedFile store(MultipartFile file) throws IOException, NoSuchAlgorithmException {

        if (file != null && !file.isEmpty()) {
                File serverFile = saveServerFile(file);

                UploadedFile uploadedFile = new UploadedFile();
                uploadedFile.setName(file.getOriginalFilename());
                uploadedFile.setPath(serverFile.getAbsolutePath());
                uploadedFile.setSize(serverFile.length());

                String hash = DatatypeConverter.printHexBinary(MessageDigest
                        .getInstance(uploadedFile.getHashMethod())
                        .digest(file.getBytes())
                ).toLowerCase();
                uploadedFile.setHash(hash);

                uploadedFileRepository.save(uploadedFile);

                return uploadedFile;
        }else{
            throw new IOException();
        }
    }

    /**
     * Saves the multipart file to file system and returns the <code>File</code> object
     *
     * @param file  A multipart file to be saved to filesystem
     * @return The <code>File</code> object, representing stored file
     * @throws IOException
     */
    private File saveServerFile(MultipartFile file) throws IOException {
        File serverFile = new File(buildAbsolutePath(file));
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
        stream.write(file.getBytes());
        stream.close();

        logger.debug("Saved file location: " + serverFile.getAbsolutePath());

        return serverFile;
    }


    /**
     * Build absolute path to save multipart file to file system
     * absolute path = upload.dir + date folder + random uuid + extension of <code>file</code>
     * @param file
     * @return absolute path
     */
    private String buildAbsolutePath(MultipartFile file) {
        String dateDir = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        File dir = new File(fileStoragePath+"/"+dateDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String pathName = dir.getAbsolutePath()
                + File.separator
                + UUID.randomUUID();
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if(extension!=null&&!extension.isEmpty()){
            pathName = pathName + "." + extension;
        }
        return pathName;
    }

    /**
     * Get file metadata from database by file id
     * @param fileId
     * @return file metadata
     */
    public UploadedFile getById(UUID fileId){
        return uploadedFileRepository.findById(fileId).get();
    }

    /**
     * Removes the file itself from filesystem and file metadata from database
     *
     * @param persistentFile - <code>UploadedFile</code> to be removed
     * @return result of file deletion operation
     */
    public Boolean removeFile(UploadedFile persistentFile) {
        File file = new File(fileStoragePath+"/"+persistentFile.getPath());
        uploadedFileRepository.delete(persistentFile);
        return file.delete();
    }

}
