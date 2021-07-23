package com.smartech.i2019.adaptiveinterviews.controller;

import com.smartech.i2019.adaptiveinterviews.api.EmployeeService;
import com.smartech.i2019.adaptiveinterviews.api.UploadFileService;
import com.smartech.i2019.adaptiveinterviews.model.Employee;
import com.smartech.i2019.adaptiveinterviews.model.UploadFile;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.Min;
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

    @Operation(summary = "Получить список файлов по ID пользователя")
    @GetMapping("/files/{id}")
    ResponseEntity<List<UploadFile>> getEmployeeFiles(@PathVariable @Min(1) int id) throws EntityNotFoundException {
        List<UploadFile> uploadFiles = employeeService.getUploadFiles(id);
        return new ResponseEntity<>(uploadFiles, HttpStatus.OK);
    }

    @Operation(summary = "Скачать файл")
    @GetMapping("/download/{id}/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable("fileId") long fileId) {
        UploadFile uploadFile = uploadFileService.findById(fileId);
        if (uploadFile == null) {
            throw new EntityNotFoundException("Файл не найден");
        }
        ByteArrayResource resource = new ByteArrayResource(uploadFile.getData());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + uploadFile.getFileName())
                .contentLength(uploadFile.getData().length) //
                .body(resource);
    }

    @Operation(summary = "Загрузить файл")
    @PostMapping("/upload/{id}")
    public ResponseEntity<String> uploadFile(@PathVariable int id, @RequestParam("file") MultipartFile file) {
        Employee employee = employeeService.findById(id);
        try {
            UploadFile uploadFile = new UploadFile();
            uploadFile.setEmployee(employee);
            uploadFile.setFileName(file.getOriginalFilename());
            uploadFile.setData(file.getBytes());
            uploadFileService.add(uploadFile);
        } catch (IOException e) {
            return null;
        }
        return ResponseEntity.ok("Файл прикреплен");
    }

    @Operation(summary = "Удалить файл")
    @DeleteMapping("/delete/{id}/{fileId}")
    ResponseEntity<String> deleteFile(@PathVariable Long fileId) {
        uploadFileService.delete(fileId);
        return ResponseEntity.ok("Файл прикреплен");
    }
}
