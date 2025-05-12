package com.example.back.util.criteriaSpecification;

import com.example.back.dto.search.BaseSearchCriteria;
import com.example.back.dto.search.MetricTimeSearchCriteria;
import com.example.back.model.Metric;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class SimpleMetricSpecification {

    public static <T> Specification<T> bySimpleCriteria(
                                                      MetricTimeSearchCriteria metricCriteria,
                                                      BaseSearchCriteria baseCriteria) {
        return (root, query, criteriaBuilder) -> {
            Join<T, Metric> metricJoin = root.join("metric");

            List<Predicate> predicates = new ArrayList<>();

            predicates.addAll(MetricTimeSpecifications.byMetricTimeCriteria(metricJoin, criteriaBuilder, metricCriteria));
            predicates.addAll(BaseEntitySpecifications.byBaseCriteria(root, query, criteriaBuilder, baseCriteria));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

