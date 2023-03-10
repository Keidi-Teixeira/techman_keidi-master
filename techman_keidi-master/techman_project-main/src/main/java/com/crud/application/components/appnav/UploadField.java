package com.crud.application.components.appnav;

import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.FileBuffer;

import java.io.InputStream;

public class UploadField extends CustomField<InputStream> {

    InputStream is;
    FileBuffer buffer = new FileBuffer();

    public UploadField() {
        Upload upload = new Upload(buffer);
        upload.setAcceptedFileTypes("image/jpeg");
        upload.setMaxFiles(1);
        upload.addSucceededListener(event -> {
            is = buffer.getInputStream();
        });

        add(upload);
    }

    public String getFileName() {
        return buffer.getFileName();
    }

    @Override
    protected InputStream generateModelValue() {
        return is;
    }

    @Override
    protected void setPresentationValue(InputStream newPresentationValue) {
    }

}
