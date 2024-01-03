package com.app.beanvalidation.extractor;

import com.app.beanvalidation.container.Entry;
import jakarta.validation.valueextraction.ExtractedValue;
import jakarta.validation.valueextraction.ValueExtractor;

// Entry<?, @ExtractedValue ?> to tell which value we want to extract
// In this case we want to extract the <V> inside Entry object
public class EntryValueExtractorValue implements ValueExtractor<Entry<?, @ExtractedValue ?>> {
    @Override
    public void extractValues(Entry<?, ?> entry, ValueReceiver valueReceiver) {
        valueReceiver.keyedValue(null, "value", entry.getValue());
    }
}