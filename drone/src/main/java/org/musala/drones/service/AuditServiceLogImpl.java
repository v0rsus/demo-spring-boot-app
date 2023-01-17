package org.musala.drones.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuditServiceLogImpl implements AuditService {
    private final Logger logger = LoggerFactory.getLogger(AuditServiceLogImpl.class);

    @Override
    public void write(String event) {
        logger.info(event);
    }
}
