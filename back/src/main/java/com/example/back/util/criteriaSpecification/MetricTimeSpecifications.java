package com.example.back.util.criteriaSpecification;

import com.example.back.config.security.components.CustomUserDetails;
import com.example.back.dto.search.MetricTimeSearchCriteria;
import com.example.back.exception.ResourceAccessDeniedException;
import com.example.back.model.Metric;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Predicate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

public class MetricTimeSpecifications {
    public static <T> List<Predicate> byMetricTimeCriteria(
            From<?, Metric> metricJoin,
            CriteriaBuilder criteriaBuilder,
            MetricTimeSearchCriteria criteria
    ) {
        if (criteria == null) {
            criteria = new MetricTimeSearchCriteria();
        }

        List<Predicate> predicates = new ArrayList<>();

        if (criteria.getMetricId() != null) {
            predicates.add(criteriaBuilder.equal(metricJoin.get("metricId"), criteria.getMetricId()));
        }

        if (criteria.getStartTime() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(metricJoin.get("timestamp"), criteria.getStartTime()));
        }

        if (criteria.getEndTime() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(metricJoin.get("timestamp"), criteria.getEndTime()));
        }

        if (criteria.getCurrentTime() != null) {
            predicates.add(criteriaBuilder.equal(metricJoin.get("timestamp"), criteria.getCurrentTime()));
        }

        if (criteria.getServerID() != null) {
            predicates.add(criteriaBuilder.equal(metricJoin.get("server").get("serverId"), criteria.getServerID()));
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()
                || !(authentication.getPrincipal() instanceof CustomUserDetails userDetails)) {
            throw new ResourceAccessDeniedException("User authentication is required to read metric history");
        }

        if (!userDetails.isAdmin()) {
            predicates.add(criteriaBuilder.equal(
                    metricJoin.get("server").get("users").get("userId"), userDetails.getId()
            ));
        }

        return predicates;
    }
}
