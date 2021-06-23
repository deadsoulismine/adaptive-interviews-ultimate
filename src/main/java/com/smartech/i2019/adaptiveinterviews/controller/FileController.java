package com.smartech.i2019.adaptiveinterviews.controller;

import com.smartech.i2019.adaptiveinterviews.dao.EmployeeDaoImpl;
import com.smartech.i2019.adaptiveinterviews.dao.UploadFileDaoImpl;
import com.smartech.i2019.adaptiveinterviews.model.UploadFile;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@Tag(name="Файлы", description="Взаимодействие с файлами")
@RequestMapping("/employees")
public class FileController {
    @Autowired
    UploadFileDaoImpl uploadFileDao;
    @Autowired
    EmployeeDaoImpl employeeDao;

    @Operation(summary = "Получить список файлов по ID пользователя")
    @GetMapping("/files/{id}")
    ResponseEntity<List<UploadFile>> getEmployeeFiles(@PathVariable @Min(1) int id) throws EntityNotFoundException {
        List<UploadFile> uploadFiles = uploadFileDao.getByEmployee(id);
        return new ResponseEntity<>(uploadFiles, HttpStatus.OK);
    }

    @Operation(summary = "Скачать файл")
    @GetMapping("/{id}/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable("fileId") long fileId) {
        UploadFile uploadFile = uploadFileDao.getById(fileId);
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
    @PostMapping("/upload")
    ResponseEntity<String> uploadFile(@RequestBody UploadFile file, @PathVariable int id) {
        file.setEmployee(employeeDao.getById(id));
        uploadFileDao.add(file);
        return ResponseEntity.ok("Файл прикреплен");
    }

    @Operation(summary = "Удалить файл")
    @DeleteMapping("/{id}/{fileId}")
    ResponseEntity<String> deleteFile(@PathVariable long fileId) {
        uploadFileDao.delete(fileId);
        return ResponseEntity.ok("Файл прикреплен");
    }
}
