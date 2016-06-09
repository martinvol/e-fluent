package com.example.user.efluent;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

class RequestBodyUtil extends RequestBody {

    private InputStream inputStream;
    private MediaType mediaType;

    public static RequestBody create(final MediaType mediaType, final InputStream inputStream) {


        return new RequestBodyUtil(inputStream, mediaType);
    }

    private RequestBodyUtil(InputStream inputStream, MediaType mediaType) {
        this.inputStream = inputStream;
        this.mediaType = mediaType;
    }

    @Override
    public MediaType contentType() {
        return mediaType;
    }

    @Override
    public long contentLength() {
        try {
            return inputStream.available();
        } catch (IOException e) {
            return 0;
        }
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        Source source = null;
        try {
            source = Okio.source(inputStream);
            sink.writeAll(source);
        } finally {
            Util.closeQuietly(source);
        }
    }
}