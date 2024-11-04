package com.picsart.common;

public interface CustomLoadableComponent<T extends CustomLoadableComponent<T>> {
    default T get() {
        try {
            isLoaded();
            return (T) this;
        } catch (Error e) {
            load();
            isLoaded();
            return (T) this;
        }
    }

    void load();

    void isLoaded() throws Error;
}
