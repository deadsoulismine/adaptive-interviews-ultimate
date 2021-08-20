package com.smartech.i2019.adaptiveinterviews.service;

import com.smartech.i2019.adaptiveinterviews.model.UploadFile;

import java.io.FileNotFoundException;
import java.util.List;

public interface UploadFileService {

    void add(UploadFile file);

    void delete(long id) throws FileNotFoundException;

    UploadFile findById(long id) throws FileNotFoundException;

    List<UploadFile> findAll();
}
