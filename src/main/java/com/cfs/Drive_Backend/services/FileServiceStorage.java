package com.cfs.Drive_Backend.services;

import com.cfs.Drive_Backend.entity.FileEntity;
import com.cfs.Drive_Backend.entity.User;
import com.cfs.Drive_Backend.repo.FileRepository;
import com.cfs.Drive_Backend.repo.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileServiceStorage {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final FileRepository fileRepository;
    private final UserRepository userRepository;

    public FileServiceStorage(FileRepository fileRepository,
                              UserRepository userRepository) {
        this.fileRepository = fileRepository;
        this.userRepository = userRepository;
    }

    public String saveFile(MultipartFile file ,  Long userId, Long parentFolderId)
    {
        //file->jo hm bhej rhe h ....Files->ik class h jo internally storage se communicate krti hai

        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Path uploadPath= Paths.get(uploadDir,"user_"+userId);  //local storage me store krne ke liye phle hm path dete hai
            if (!Files.exists(uploadPath))     // kaise check krte hai Files.exists se
            {
                Files.createDirectories(uploadPath);   //agar nhi h to create krenge
            }
            String fileName= file.getOriginalFilename();     //file name nikal rhe h
            Path filePath = uploadPath.resolve(fileName);    //ye path bna rha h file name se

            //Files->jo communicator hai.... ye file lega path lega or agar alredy h toh  replace krega o orr
            // copy file to storage
            Files.copy(file.getInputStream(),filePath, StandardCopyOption.REPLACE_EXISTING);
            //meta data for DB
            FileEntity fileEntity=new FileEntity();
            fileEntity.setName(fileName);
            fileEntity.setPath(filePath.toString());
            fileEntity.setSize(file.getSize());
            fileEntity.setType("file");
            fileEntity.setParentFolderId(parentFolderId);
            fileEntity.setCreatedAt(LocalDateTime.now());

            fileEntity.setOwner(user);

            fileRepository.save(fileEntity);

            return "File uploaded Successfully";
        }catch (IOException e)
        {
            e.printStackTrace();
            return "File not uploaded";
        }

    }

    public List<FileEntity> getFilesInFolder(Long userId, Long parentFolderId)
    {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<FileEntity> files = fileRepository.findByOwner(user);
        if(parentFolderId == null)
        {
            return files
                    .stream()
                    .filter(f->f.getParentFolderId()==null)
                    .collect(Collectors.toList());
        }else {
            return  files
                    .stream()
                    .filter(f-> parentFolderId.equals(f.getParentFolderId()))
                    .collect(Collectors.toList());  //jitne bhi pre h file use collect krke list me bhej dunga
        }
    }

    public FileEntity getFileById(Long id, Long userId)
    {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        FileEntity file = fileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found"));

        if(!file.getOwner().getId().equals(user.getId()))
        {
            throw new RuntimeException("Access denied");
        }

        return file;
    }
    public void deleteById(Long id, Long userId)
    {
        FileEntity file = getFileById(id, userId);

        Path path = Paths.get(file.getPath());

        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file");
        }

        fileRepository.delete(file);
    }

}
