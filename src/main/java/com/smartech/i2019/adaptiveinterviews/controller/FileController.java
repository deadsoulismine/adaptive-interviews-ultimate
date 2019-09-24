package com.smartech.i2019.adaptiveinterviews.controller;

import com.smartech.i2019.adaptiveinterviews.dao.EmployeeDaoImpl;
import com.smartech.i2019.adaptiveinterviews.dao.UploadFileDaoImpl;
import com.smartech.i2019.adaptiveinterviews.model.Employee;
import com.smartech.i2019.adaptiveinterviews.model.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.Min;
import java.io.IOException;

@Controller
public class FileController {
    @Autowired
    UploadFileDaoImpl uploadFileDao;
    @Autowired
    EmployeeDaoImpl employeeDao;

    @PostMapping("/employees/{id}/upload")
    public String addFile(@RequestBody UploadFile uploadFile, @PathVariable @Min(1) int id) {
        uploadFileDao.add(uploadFile);
        return "redirect:/employees/" + id;
    }

    @GetMapping("/employees/{id}/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadFile(
            @PathVariable("id") int id, @PathVariable("fileId") long fileId) {
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
    @GetMapping("/employees/{id}/upload")
    public String uploadFileForm(@PathVariable int id, Model model) {
        Employee employee = employeeDao.getById(id);
        if (employee == null) {
            throw new EntityNotFoundException("Сотрудник не найден");
        }
        model.addAttribute("employee", employee);
        return "upload";
    }

    @PostMapping("/employees/{id}/uploadfile")
    public String saveFile(@PathVariable int id, Model model, @RequestParam("file") MultipartFile file,
                           RedirectAttributes redirectAttributes) {
        Employee employee = employeeDao.getById(id);
        if (employee == null) {
            throw new EntityNotFoundException("Сотрудник не найден");
        }
        model.addAttribute("employee", employee);
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Выберите файл для загрузки");
            model.addAttribute("employee", employee);
            return "redirect:upload";
        }
        try {
            UploadFile uploadFile = new UploadFile();
            uploadFile.setEmployee(employee);
            uploadFile.setFileName(file.getOriginalFilename());
            uploadFile.setData(file.getBytes());
            uploadFileDao.add(uploadFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/employees/" + id;
    }
}
