package com.example.demo.common.reader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;

public class CachedBodyServletInputStream extends ServletInputStream {

    private final InputStream cachedInputStream;

    public CachedBodyServletInputStream(String body) {
        byte[] bytes = body.getBytes();
        this.cachedInputStream = new ByteArrayInputStream(bytes);
    }

    @Override
    public int read() throws IOException {
        return cachedInputStream.read();
    }

    @Override
    public boolean isFinished() {
        try {
            return cachedInputStream.available() == 0;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener readListener) {
        // Not implemented for simplicity
    }
}

