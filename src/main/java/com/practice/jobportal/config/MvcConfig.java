package com.practice.jobportal.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    private static final String UPLOAD_DIR = "photos";

    //Se sobrescribe el metodo original para guardar el archivo de forma custom
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        exposeDirectory(UPLOAD_DIR, registry);
    }

    /*TODO OJO: este metodo convierte uploadDir  que es un string de la imagen a un path con este patron("/photos/**)
    * Los ** significa  que podra machear con todos los sub-directorios
    */
    private void exposeDirectory(String uploadDir, ResourceHandlerRegistry registry){
        Path path = Paths.get(uploadDir);
        registry.addResourceHandler("/"+uploadDir+"/**").addResourceLocations("file:"+path.toAbsolutePath()+"/");

    }
}
