package com.cfs.Drive_Backend.controller;

import com.cfs.Drive_Backend.entity.FileEntity;
import com.cfs.Drive_Backend.entity.User;
import com.cfs.Drive_Backend.services.FileServiceStorage;

import jakarta.servlet.http.HttpSession;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/files")
@CrossOrigin(origins = "http://localhost:3000")
public class FileController {
    private final FileServiceStorage fileServiceStorage;


    public FileController(FileServiceStorage fileServiceStorage) {
        this.fileServiceStorage = fileServiceStorage;

    }

    private User getLoggedUser(HttpSession session) {
        return (User) session.getAttribute("user");
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file")MultipartFile file,

                                             @RequestParam(value = "parentFolderId",required = false) Long parentFolderId,
                                             HttpSession session)
    {
        User user= getLoggedUser(session);

        if(user== null)
        {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        try
        {
            String response=fileServiceStorage.saveFile(file,user.getId(),parentFolderId);
            return ResponseEntity.ok(response);
        }
        catch(Exception e)
        {
            return ResponseEntity.status(500).body("File upload failed!");
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource>downloadFile(@PathVariable Long id,
                                                HttpSession session)
    {
        User user = getLoggedUser(session);
        if(user==null)
        {
            return ResponseEntity.status(401).build();
        }
        try{
            //mysql meta data use
            FileEntity fileEntity = fileServiceStorage.getFileById(id,user.getId());
            Path path = Paths.get(fileEntity.getPath());
            Resource resource= new UrlResource(path.toUri());
            return ResponseEntity.ok().header("content-Disposition","attachment; filename=\""+fileEntity.getName()+"\"")
                    .body(resource);
        }catch(Exception e)
        {
            return ResponseEntity.status(404).build();
        }
    }
    @GetMapping("/list")
    public ResponseEntity<?> listFiles(

            @RequestParam(value="parentFolderId", required=false) Long parentFolderId,
            HttpSession session)
    {
        User user = getLoggedUser(session);

        if (user == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        List<FileEntity> files = fileServiceStorage.getFilesInFolder(user.getId(), parentFolderId);
        return ResponseEntity.ok(files);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFile(@PathVariable Long id,
                                             HttpSession session)
    {
        User user = getLoggedUser(session);

        if (user == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        try{
            fileServiceStorage.deleteById(id, user.getId());
            return ResponseEntity.ok("File deleted Successfully");
        }
        catch (Exception e)
        {
            return ResponseEntity.status(500).body("Failed to delete file");
        }
    }

}
