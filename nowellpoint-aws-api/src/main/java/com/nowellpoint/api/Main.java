package com.nowellpoint.api;

import java.util.Arrays;
import java.util.Optional;
import java.util.TimeZone;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.wildfly.swarm.logging.LoggingFraction;

import com.nowellpoint.aws.model.admin.Properties;

public class Main {
	
	public static void main(String[] args) throws Exception {
		
		//
		// set system properties from command line args
		//
		
		if (args != null && args.length > 0) {
			Arrays.asList(args).stream().forEach(a -> {
				if (a.startsWith("-D")) {
					String[] param = a.replace("-D", "").split("=");
					System.setProperty(param[0], param[1]);
				}
			});
		}
		
		//
		// set default system properties
		//
		
		System.setProperty("swarm.http.port", getHttpPort());
		System.setProperty("swarm.https.port", getHttpsPort());
		System.setProperty("swarm.https.certificate.generate", "true");

		//
		// build the container
		//
		
        Swarm container = new Swarm(); 
        
		//
        // set system properties from configuration
        //

        Properties.setSystemProperties(container
                .stageConfig()
                .resolve("propertyStore.name")
                .getValue());
        
        //
        // set default timezone to UTC
        //
        
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        
        //
        // create the JAX-RS deployment archive
        // 
        
        JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class, "nowellpoint-api.war")
        		.addPackages(true, Package.getPackage("com.nowellpoint.api"))
        		.addAsWebInfResource(new ClassLoaderAsset("WEB-INF/web.xml", Main.class.getClassLoader()), "web.xml")
        		.addAsWebResource(new ClassLoaderAsset("ValidationMessages.properties", Main.class.getClassLoader()), "ValidationMessages.properties")
        		.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
        
        deployment.addAllDependencies();
        
        //
        // start the container and deploy the archives
        //

        container.fraction(LoggingFraction.createDefaultLoggingFraction()).start().deploy(deployment);

    }
	
	public static String getHttpPort() {
		return Optional.ofNullable(System.getenv().get("PORT")).orElse("5000");
	}
	
	public static String getHttpsPort() {
		return String.valueOf(Integer.valueOf(getHttpPort()) + 100);
	}
}