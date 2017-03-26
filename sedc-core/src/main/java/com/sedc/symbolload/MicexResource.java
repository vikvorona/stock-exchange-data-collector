package com.sedc.symbolload;

import org.springframework.core.io.UrlResource;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class MicexResource extends UrlResource {

    public MicexResource(URI uri) throws MalformedURLException {
        super(uri);
    }

    public MicexResource(URL url) {
        super(url);
    }

    public MicexResource(String path) throws MalformedURLException {
        super(path);
    }

    public MicexResource(String protocol, String location) throws MalformedURLException {
        super(protocol, location);
    }

    public MicexResource(String protocol, String location, String fragment) throws MalformedURLException {
        super(protocol, location, fragment);
    }

    @Override
    public boolean exists() {
        return true;
    }
}
