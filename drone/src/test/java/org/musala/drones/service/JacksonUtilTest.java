package org.musala.drones.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class JacksonUtilTest {
    @Test
    void instanceTest() {
        Assertions.assertNotNull(JacksonUtil.INSTANCE);
    }
}
