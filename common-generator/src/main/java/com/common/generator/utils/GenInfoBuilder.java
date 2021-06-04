package com.common.generator.utils;

import com.common.generator.model.GenInfo;

/**
 * @author: LX
 */
public class GenInfoBuilder {

    private GenInfo genInfo;

    private GenInfoBuilder() {
    }

    public static GenInfoBuilder build() {
        GenInfoBuilder instance = new GenInfoBuilder();
        instance.genInfo = new GenInfo();
        return instance;
    }
}
