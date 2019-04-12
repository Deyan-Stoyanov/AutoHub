package com.autohub.util;

import com.autohub.domain.enums.LogType;
import com.autohub.domain.model.service.LogServiceModel;
import com.autohub.service.interfaces.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.logging.Logger;

@Component
public class AutoHubLogger extends Logger {
    private final LogService logService;

    @Autowired
    public AutoHubLogger(LogService logService) {
        super("logger", null);
        this.logService = logService;
    }

    @Override
    public void info(String msg) {
        super.info(msg);
        this.logService.save(new LogServiceModel(msg, LogType.INFO));
    }

    @Override
    public void severe(String msg) {
        super.severe(msg);
        this.logService.save(new LogServiceModel(msg, LogType.ERROR));
    }
}
