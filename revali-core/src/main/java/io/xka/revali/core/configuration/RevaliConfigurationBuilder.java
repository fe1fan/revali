package io.xka.revali.core.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.xka.revali.core.TypeReference;

import java.io.File;

public class RevaliConfigurationBuilder<T extends RevaliConfigurationTarget> {

    @SuppressWarnings("all")
    public static <T extends RevaliConfigurationTarget> T configs(TypeReference<T> typeReference, RevaliConfiguration<T>... configurations) {
        Class<? extends RevaliConfigurationTarget> rawClass = (Class<? extends RevaliConfigurationTarget>) typeReference.getType();
        try {
            T target = (T) rawClass.getDeclaredConstructor().newInstance();
            for (RevaliConfiguration<T> configuration : configurations) {
                configuration.configure(target);
            }
            return target;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("all")
    public static <T extends RevaliConfigurationTarget> T yaml(TypeReference<T> typeReference, String yaml) {
        //check file exists
        File file = new File(yaml);
        if (!file.exists()) {
            throw new RuntimeException("File " + yaml + " does not exist");
        }
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        Class<? extends RevaliConfigurationTarget> rawClass = (Class<? extends RevaliConfigurationTarget>) typeReference.getType();
        try {
            T target = (T) rawClass.getDeclaredConstructor().newInstance();
            Class<? extends RevaliConfiguration<? extends RevaliConfigurationTarget>> configurationClass = target.getConfigurationClass();
            RevaliConfiguration<? extends RevaliConfigurationTarget> revaliConfiguration = mapper.readValue(file, configurationClass);
            target.setConfiguration(revaliConfiguration);
            return target;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
