package org.musala.drones.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.slf4j.Logger;

class AuditServiceLogImplTest {
    private final AuditServiceLogImpl service = new AuditServiceLogImpl();

    @Test
    void writeTest() {
        Logger logger = Mockito.mock(Logger.class);
        Whitebox.setInternalState(service, "logger", logger);

        service.write("any");
        Mockito.verify(logger, Mockito.times(1)).info("any");
    }
}
