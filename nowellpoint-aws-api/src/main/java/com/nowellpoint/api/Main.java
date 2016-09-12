package com.nowellpoint.api;

import java.io.File;
import java.util.Optional;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.jaxrs.JAXRSArchive;

import org.wildfly.swarm.logging.LoggingFraction;

import com.nowellpoint.aws.model.admin.Properties;

public class Main {
	
	public static void main(String[] args) throws Exception {
		
		//
		// set system properties
		//
		
		System.setProperty("swarm.http.port", getPort());

		//
		// build and start the container
		//
		
        Swarm container = new Swarm();
        
		//
        // set system properties from configuration
        //

        Properties.setSystemProperties(System.getenv("NCS_PROPERTY_STORE"));
        
        //
        // create the JAX-RS deployment archive
        // 
        
        JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class, "nowellpoint-api.war")
        		.addPackages(true, Package.getPackage("com.nowellpoint.api"))
        		.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
        		.addAsWebInfResource(new File("src/main/resources/WEB-INF/web.xml"), "web.xml")
        		.addAsWebResource(new File("src/main/resources/ValidationMessages.properties"));
        
        deployment.addAllDependencies();
        
        //
        // start the container and deploy the archives
        //

        container.fraction(LoggingFraction.createDefaultLoggingFraction()).start().deploy(deployment);
    }
	
	private static String getPort() {
		return Optional.ofNullable(System.getenv().get("PORT")).orElse("9090");
	}
}