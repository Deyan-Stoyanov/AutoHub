package com.autohub.scheduled;

import com.autohub.domain.model.service.LogServiceModel;
import com.autohub.service.interfaces.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class LoggerCleaner {
    private final LogService logService;

    @Autowired
    public LoggerCleaner(LogService logService) {
        this.logService = logService;
    }

    @Scheduled(cron = "0 5 * * *")
    public void cleanUpLogs(){
        List<LogServiceModel> allLogs = this.logService.findAll();
        for (LogServiceModel log:allLogs) {
            if(log.getDate().getDayOfYear() < LocalDateTime.now().getDayOfYear() - 7){
                this.logService.deleteById(log.getId());
            }
        }
    }
}
