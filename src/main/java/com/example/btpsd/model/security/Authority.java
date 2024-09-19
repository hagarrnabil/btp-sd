//package com.example.btpsd.model.security;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.util.Set;
//@Setter
//@Getter
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//@Entity
//public class Authority {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Integer id;
//
//    private String permission;
//
//    @ManyToMany(mappedBy = "authorities")
//    private Set<Role>roles;
//}