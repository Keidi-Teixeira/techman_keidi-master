package com.crud.application.components.appnav;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.UploadI18N;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import org.apache.commons.lang3.ObjectUtils;

import java.io.InputStream;
import java.io.Serial;

public class UploadField extends Upload {
    @Serial
    private static final long serialVersionUID = 8179161183407646700L;
    public static final int LIMITE_PADRAO = 10485760;
    private MemoryBuffer memoryBuffer;
    private String addFilesManyLabel, addFilesOneLabel;
    private String fileIsTooBigLabel, incorrectFileTypeLabel, tooManyFilesLabel;
    private String dropFilesManyLabel, dropFilesOneLabel;

    public UploadField() {
        this.withMaxFileSize(LIMITE_PADRAO);
        this.setReceiver(memoryBuffer = new MemoryBuffer());
    }

    public UploadField(final MultiFileMemoryBuffer receiver) {
        super(receiver);
        this.withMaxFileSize(LIMITE_PADRAO);
    }

    public UploadField withMaxFiles(final int maxFiles) {
        setMaxFiles(maxFiles);
        return this;
    }

    public UploadField withMaxFileSize(final int maxFileSize) {
        setMaxFileSize(maxFileSize);
        return this;
    }

    public UploadField withAddFilesManyLabel(final String addFilesManyLabel) {
        this.addFilesManyLabel = addFilesManyLabel;
        return this;
    }

    public UploadField withAddFilesOneLabel(final String addFilesOneLabel) {
        this.addFilesOneLabel = addFilesOneLabel;
        return this;
    }

    public UploadField withFileIsTooBigLabel(final String fileIsTooBigLabel) {
        this.fileIsTooBigLabel = fileIsTooBigLabel;
        return this;
    }

    public UploadField withIncorrectFileTypeLabel(final String incorrectFileTypeLabel) {
        this.incorrectFileTypeLabel = incorrectFileTypeLabel;
        return this;
    }

    public UploadField withTooManyFilesLabelLabel(final String tooManyFilesLabel) {
        this.tooManyFilesLabel = tooManyFilesLabel;
        return this;
    }

    public UploadField withDropFilesManyLabel(final String dropFilesManyLabel) {
        this.dropFilesManyLabel = dropFilesManyLabel;
        return this;
    }

    public UploadField withDropFilesOneLabel(final String dropFilesOneLabel) {
        this.dropFilesOneLabel = dropFilesOneLabel;
        return this;
    }

    public MemoryBuffer getMemoryBuffer() {
        return memoryBuffer;
    }

    public InputStream getInputStream() {
        return memoryBuffer.getInputStream();
    }

    public String getFileName() {
        return memoryBuffer.getFileName();
    }

    public UploadField clear() {
        super.clearFileList();
        this.setReceiver(this.memoryBuffer = new MemoryBuffer());
        return this;
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        final UploadI18N uploadI18N = new UploadI18N();
        uploadI18N.setAddFiles(new UploadI18N.AddFiles());
        uploadI18N.getAddFiles().setMany(ObjectUtils.firstNonNull(this.addFilesManyLabel, "Upload arquivos"));
        uploadI18N.getAddFiles().setOne(ObjectUtils.firstNonNull(this.addFilesOneLabel, "Upload arquivo"));
        uploadI18N.setError(new UploadI18N.Error());
        uploadI18N.getError().setFileIsTooBig(ObjectUtils.firstNonNull(this.fileIsTooBigLabel, "Arquivo grande demais"));
        uploadI18N.getError().setIncorrectFileType(ObjectUtils.firstNonNull(this.incorrectFileTypeLabel, "Tipo incorreto de arquivo"));
        uploadI18N.getError().setTooManyFiles(ObjectUtils.firstNonNull(this.tooManyFilesLabel, "Quantidade máxima de arquivos ultrapassada"));
        uploadI18N.setDropFiles(new UploadI18N.DropFiles());
        uploadI18N.getDropFiles().setMany(ObjectUtils.firstNonNull(this.dropFilesManyLabel, "Arraste o arquivo aqui"));
        uploadI18N.getDropFiles().setOne(ObjectUtils.firstNonNull(this.dropFilesOneLabel, "Arraste os arquivos aqui"));
        this.setI18n(uploadI18N);
    }
}
