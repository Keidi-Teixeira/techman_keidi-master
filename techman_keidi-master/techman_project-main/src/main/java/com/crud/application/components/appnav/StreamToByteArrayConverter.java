package com.crud.application.components.appnav;

import com.google.common.io.ByteStreams;
import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamToByteArrayConverter implements Converter<InputStream, byte[]> {

    @Override
    public Result<byte[]> convertToModel(InputStream value, ValueContext context) {
        try {
            return Result.ok(ByteStreams.toByteArray(value));
        } catch (IOException e) {
            return Result.error("Error converting input stream to byte array");
        }
    }

    @Override
    public InputStream convertToPresentation(byte[] value, ValueContext context) {
        if (value == null) {
            return null;
        }
        return new ByteArrayInputStream(value);
    }
}