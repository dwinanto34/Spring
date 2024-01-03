package com.app.beanvalidation.extractor;

import com.app.beanvalidation.container.DataInteger;
import jakarta.validation.valueextraction.ExtractedValue;
import jakarta.validation.valueextraction.UnwrapByDefault;
import jakarta.validation.valueextraction.ValueExtractor;

// UnwrapByDefault annotation indicates that unwrapping should be performed.
@UnwrapByDefault
public class DataIntegerValueExtractor implements ValueExtractor<@ExtractedValue(type = Integer.class) DataInteger> {
    @Override
    public void extractValues(@ExtractedValue(type = Integer.class) DataInteger dataInteger, ValueReceiver valueReceiver) {
        valueReceiver.value(null, dataInteger.getData());
    }
}
