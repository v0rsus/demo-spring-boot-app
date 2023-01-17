package org.musala.drones;

import org.musala.drones.service.AuditService;
import org.musala.drones.service.AuditServiceLogImpl;
import org.musala.drones.service.drone.DroneFactory;
import org.musala.drones.service.drone.DroneFactoryImpl;
import org.musala.drones.service.drone.DroneService;
import org.musala.drones.service.drone.DroneServiceImpl;
import org.musala.drones.service.medication.MedicationFactory;
import org.musala.drones.service.medication.MedicationFactoryImpl;
import org.musala.drones.service.medication.MedicationService;
import org.musala.drones.service.medication.MedicationServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableJpaRepositories(basePackages = "org.musala.drones.repository")
@EnableScheduling
public class DroneConfiguration {

    @Bean
    public DroneFactory droneFactory()
    {
        return new DroneFactoryImpl();
    }

    @Bean
    public DroneService droneService()
    {
        return new DroneServiceImpl();
    }

    @Bean
    public MedicationFactory medicationFactory() {
        return new MedicationFactoryImpl();
    }

    @Bean
    public MedicationService medicationService(){
        return new MedicationServiceImpl();
    }

    @Bean
    public AuditService auditService()
    {
        return new AuditServiceLogImpl();
    }
}
