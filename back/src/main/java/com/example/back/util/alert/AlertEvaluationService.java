package com.example.back.util.alert;

import com.example.back.dto.request.MetricDTORequest;
import com.example.back.model.Alert;
import com.example.back.model.AlertThreshold;
import com.example.back.model.Server;
import com.example.back.model.enums.MetricKey;
import com.example.back.model.enums.PreferredLanguage;
import com.example.back.repository.AlertRepository;
import com.example.back.repository.AlertThresholdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertEvaluationService {

    private final MetricExtractorsRegistry registry;
    private final AlertThresholdRepository alertThresholdRepository;
    private final AlertRepository alertRepository;

    @Autowired
    public AlertEvaluationService(MetricExtractorsRegistry registry,
                                  AlertThresholdRepository alertThresholdRepository,
                                  AlertRepository alertRepository) {
        this.registry = registry;
        this.alertThresholdRepository = alertThresholdRepository;
        this.alertRepository = alertRepository;
    }

    public void evaluateAndSaveAlerts(Server server, MetricDTORequest metricDTO, PreferredLanguage language) {
        List<AlertThreshold> thresholds = alertThresholdRepository.findAllByServer_ServerId(server.getServerId());

        for (AlertThreshold threshold : thresholds) {
            MetricKey key = threshold.getMetricKey();

            List<Double> actualValues = registry.extractValues(key, metricDTO);
            if (actualValues == null || actualValues.isEmpty()) continue;

            for (Double value : actualValues) {
                if (value != null && value >= threshold.getThreshold()) {
                    String message = generateAlertMessage(key, value, language);

                    Alert alert = new Alert();
                    alert.setServer(server);
                    alert.setThreshold(threshold);
                    alert.setAlertMessage(message);
                    System.out.println("Alert: " + message);
                    alertRepository.save(alert);
                }
            }
        }
    }

    private String generateAlertMessage(MetricKey key, double value, PreferredLanguage language) {
        return String.format(key.getMessageTemplate(language), value);
    }
}
