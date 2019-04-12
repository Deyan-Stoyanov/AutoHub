package com.autohub.scheduled;

import com.autohub.domain.enums.AdvertisementStatus;
import com.autohub.domain.model.service.CarAdvertisementServiceModel;
import com.autohub.domain.model.service.PartAdvertisementServiceModel;
import com.autohub.service.interfaces.CarAdvertisementService;
import com.autohub.service.interfaces.PartAdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdvertisementCleaner {
    private final CarAdvertisementService carAdvertisementService;
    private final PartAdvertisementService partAdvertisementService;

    @Autowired
    public AdvertisementCleaner(CarAdvertisementService carAdvertisementService, PartAdvertisementService partAdvertisementService) {
        this.carAdvertisementService = carAdvertisementService;
        this.partAdvertisementService = partAdvertisementService;
    }

    @Scheduled(cron = "0 4 * * 2")
    public void cleanupAdvertisements() {
        List<CarAdvertisementServiceModel> allCars = this.carAdvertisementService.findAll();
        for (CarAdvertisementServiceModel car : allCars) {
            if (car.getStatus().equals(AdvertisementStatus.DECLINED)) {
                this.carAdvertisementService.deleteById(car.getId());
            }
        }
        List<PartAdvertisementServiceModel> allParts = this.partAdvertisementService.findAll();
        for (PartAdvertisementServiceModel part : allParts) {
            if (part.getStatus().equals(AdvertisementStatus.DECLINED)) {
                this.partAdvertisementService.deleteById(part.getId());
            }
        }
    }
}
