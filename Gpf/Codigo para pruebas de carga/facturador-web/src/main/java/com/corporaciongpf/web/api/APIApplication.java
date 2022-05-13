package com.corporaciongpf.web.api;

import javax.ws.rs.ApplicationPath;


import javax.ws.rs.core.Application;

import com.corporaciongpf.web.resource.FacturaResource;

import java.util.HashSet;
import java.util.Set;


@ApplicationPath("/api")
public class APIApplication extends Application {
    public Set<Class<?>> getClasses() {
        Set<Class<?>> set = new HashSet<>();
        set.add( FacturaResource.class );
        return set;
    }
}


