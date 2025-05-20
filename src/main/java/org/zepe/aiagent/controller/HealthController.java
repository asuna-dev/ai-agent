package org.zepe.aiagent.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zzpus
 * @datetime 2025/5/20 17:29
 * @description
 */
@Slf4j
@RestController
public class HealthController {
    @GetMapping("/health")
    public String health() {
        log.info("health check");
        return "ok";
    }
}
