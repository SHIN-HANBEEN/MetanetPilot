package com.example.demo.common.reader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

public class ReadableRequestBodyWrapper extends HttpServletRequestWrapper {
	  class ServletInputStreamImpl extends ServletInputStream {
	    private InputStream inputStream;

	    public ServletInputStreamImpl(final InputStream inputStream) {
	      this.inputStream = inputStream;
	    }

	    @Override
	    public boolean isFinished() {
	      // TODO Auto-generated method stub
	      return false;
	    }

	    @Override
	    public boolean isReady() {
	      // TODO Auto-generated method stub
	      return false;
	    }

	    @Override
	    public int read() throws IOException {
	      return this.inputStream.read();
	    }

	    @Override
	    public int read(final byte[] b) throws IOException {
	      return this.inputStream.read(b);
	    }

	    @Override
	    public void setReadListener(final ReadListener listener) {
	      // TODO Auto-generated method stub
	    }
	  }

	  private byte[] bytes;
	  private String requestBody;

	  public ReadableRequestBodyWrapper(final HttpServletRequest request) throws IOException {
	    super(request);

	    InputStream in = super.getInputStream();
	    // request의 InputStream의 content를 byte array로 가져오고
	    this.bytes = IOUtils.toByteArray(in);
	    // 그 데이터는 따로 저장한다
	    this.requestBody = new String(this.bytes);
	  }

	  @Override
	  public ServletInputStream getInputStream() throws IOException {
	    // InputStream을 반환해야하면 미리 구해둔 byte array 로
	    // 새 InputStream을 만들고 이걸로 ServletInputStream을 새로 만들어 반환
	    final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.bytes);
	    return new ServletInputStreamImpl(byteArrayInputStream);
	  }

	  public String getRequestBody() {
	    return this.requestBody;
	  }
	}
