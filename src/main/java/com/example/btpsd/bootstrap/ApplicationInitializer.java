//package com.example.btpsd.bootstrap;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.cloud.Cloud;
//import org.springframework.cloud.CloudException;
//import org.springframework.cloud.CloudFactory;
//import org.springframework.context.ApplicationContextInitializer;
//import org.springframework.core.env.ConfigurableEnvironment;
//import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
//
//public class ApplicationInitializer implements ApplicationContextInitializer<AnnotationConfigWebApplicationContext> {
//
//    private static final Log logger = LogFactory.getLog(ApplicationInitializer.class);
//
//    @Override
//    public void initialize(AnnotationConfigWebApplicationContext applicationContext) {
//        Cloud cloud = getCloud();
//        ConfigurableEnvironment appEnvironment = applicationContext.getEnvironment();
//        if (cloud!=null) {
//            appEnvironment.addActiveProfile("cloud");
//        }
//        logger.info("Cloud profile active");
//    }
//
//    private Cloud getCloud() {
//        try {
//            CloudFactory cloudFactory = new CloudFactory();
//            return cloudFactory.getCloud();
//        } catch (CloudException ce) {
//            return null;
//        }
//    }
//
//}
