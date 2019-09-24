package com.smartech.i2019.adaptiveinterviews.api;

import com.smartech.i2019.adaptiveinterviews.model.UploadFile;

public interface UploadFileDao {
    void add(UploadFile file);

    void delete(long id);

    UploadFile getById(long id);
}
