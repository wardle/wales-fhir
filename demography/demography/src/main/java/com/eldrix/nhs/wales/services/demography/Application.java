package com.eldrix.nhs.wales.services.demography;

import com.google.inject.Binder;
import com.google.inject.Module;

import io.bootique.Bootique;
import io.bootique.jetty.JettyModule;


public class Application implements Module
{
    public static void main( String[] args )
    {
    	Bootique.app(args).module(Application.class).autoLoadModules().run();
    }
    
    public void configure(Binder binder) {
    	JettyModule.contributeServlets(binder).addBinding().to(ExampleRestfulServlet.class);
    }

}
