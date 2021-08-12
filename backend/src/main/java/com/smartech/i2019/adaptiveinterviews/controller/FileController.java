package com.smartech.i2019.adaptiveinterviews.controller;

import com.smartech.i2019.adaptiveinterviews.api.EmployeeService;
import com.smartech.i2019.adaptiveinterviews.api.UploadFileService;
import com.smartech.i2019.adaptiveinterviews.model.Employee;
import com.smartech.i2019.adaptiveinterviews.model.UploadFile;
import com.smartech.i2019.adaptiveinterviews.util.exception.EmployeeFilesNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:8081"})
@RestController
@Tag(name = "Файлы", description = "Взаимодействие с файлами")
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class FileController {
    private final UploadFileService uploadFileService;
    private final EmployeeService employeeService;
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Operation(summary = "Получить список файлов по ID пользователя")
    @GetMapping("/files/{id}")
    List<UploadFile> getEmployeeFiles(@PathVariable @Min(1) int id) throws EmployeeFilesNotFoundException {
        return employeeService.getUploadFiles(id);
    }

    @Operation(summary = "Скачать файл")
    @GetMapping("/{id}/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable("fileId") long fileId) throws FileNotFoundException {
        UploadFile uploadFile = uploadFileService.findById(fileId);
        ByteArrayResource resource = new ByteArrayResource(uploadFile.getData());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + uploadFile.getFileName())
                .contentLength(uploadFile.getData().length) //
                .body(resource);
    }

    @Operation(summary = "Загрузить файл")
    @PostMapping("/{id}")
    public String uploadFile(@PathVariable int id, @RequestParam("file") MultipartFile file) {
        Employee employee = employeeService.findById(id);
        try {
            UploadFile uploadFile = new UploadFile();
            uploadFile.setEmployee(employee);
            uploadFile.setFileName(file.getOriginalFilename());
            uploadFile.setData(file.getBytes());
            uploadFileService.add(uploadFile);
        } catch (IOException e) {
            logger.error("Не удалось загрузить файл!", e);
            return null;
        }
        return "Файл прикреплен";
    }

    @Operation(summary = "Удалить файл")
    @DeleteMapping("/{id}/{fileId}")
    @Secured("ROLE_ADMIN")
    String deleteFile(@PathVariable Long fileId) {
        uploadFileService.delete(fileId);
        return "Файл прикреплен";
    }
}
