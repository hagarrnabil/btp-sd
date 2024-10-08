//package com.example.btpsd.bootstrap;
//
//import com.example.btpsd.model.LineType;
//import com.example.btpsd.repositories.LineTypeRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class LineTypeInitializer {
//
//    @Bean
//    public CommandLineRunner loadLineTypes(LineTypeRepository repository) {
//        return args -> {
//            repository.save(new LineType(1L,"Standard line", "Default line type"));
//            repository.save(new LineType(2L,"Informatory line", "Informational line"));
//            repository.save(new LineType(3L,"Internal line", "Internal usage line"));
//            repository.save(new LineType(4L,"Contingency line", "Backup or contingency line"));
//        };
//    }
//}
