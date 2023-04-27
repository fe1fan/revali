package io.xka.revali.core;

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
}
