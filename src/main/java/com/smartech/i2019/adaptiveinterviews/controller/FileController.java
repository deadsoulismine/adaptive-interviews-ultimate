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
@RequestMapping("/employees")
public class FileController {
    @Autowired
    UploadFileDaoImpl uploadFileDao;
    @Autowired
    EmployeeDaoImpl employeeDao;

    @PostMapping("/upload")
    public String addFile(@RequestParam("file") UploadFile uploadFile, @PathVariable @Min(1) int id) {
        uploadFileDao.add(uploadFile);
        return "redirect:/" + id;
    }

    @GetMapping("/{fileId}/{id}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable("id") int id) {
        UploadFile uploadFile = uploadFileDao.getById(id);
        if (uploadFile == null) {
            throw new EntityNotFoundException("Файл не найден");
        }
        ByteArrayResource resource = new ByteArrayResource(uploadFile.getData());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + uploadFile.getFileName())
                .contentLength(uploadFile.getData().length) //
                .body(resource);
    }

    @GetMapping("/upload/{id}")
    public String uploadFileForm(@PathVariable int id, Model model) {
        Employee employee = employeeDao.getById(id);
        if (employee == null) {
            throw new EntityNotFoundException("Сотрудник не найден");
        }
        model.addAttribute("employee", employee);
        return "upload";
    }

    @PostMapping("/upload/uploadfile/{id}")
    public String saveFile(@PathVariable("id") int id, Model model, @RequestParam("file") MultipartFile file,
                           RedirectAttributes redirectAttributes) {
        Employee employee = employeeDao.getById(id);
        if (employee == null) {
            throw new EntityNotFoundException("Сотрудник не найден");
        }
        model.addAttribute("employee", employee);
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Выберите файл для загрузки");
            model.addAttribute("employee", employee);
            return "redirect:upload/{id}";
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

    @GetMapping("/deletefile/{fileid}/{id}")
    public String deleteFile(@PathVariable("fileid") long fileId, @PathVariable("id") int id) {
        uploadFileDao.delete(id);
        return "redirect:/employees/" + fileId;
    }
}
