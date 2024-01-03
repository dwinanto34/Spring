package com.app.beanvalidation.extractor;

import com.app.beanvalidation.container.Data;
import jakarta.validation.valueextraction.ExtractedValue;
import jakarta.validation.valueextraction.ValueExtractor;

// <@ExtractedValue ?> to tell which value we want to extract
// In this case we want to extract the T inside Data object
public class DataValueExtractor implements ValueExtractor<Data<@ExtractedValue ?>> {
    @Override
    public void extractValues(Data<?> data, ValueReceiver valueReceiver) {
        valueReceiver.value(null, data.getData());
    }
}