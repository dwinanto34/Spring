package com.app.beanvalidation.container;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SampleEntry {
    private Entry<@NotBlank @Size(min=10, max=100) String, @NotBlank @Size(min=10, max=100) String> entry;

    public Entry<String, String> getEntry() {
        return entry;
    }

    public void setEntry(Entry<String, String> entry) {
        this.entry = entry;
    }
}