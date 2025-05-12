package com.example.back.util.criteriaSpecification;

import com.example.back.dto.search.DiskIOSearchCriteria;
import com.example.back.model.DiskIO;
import com.example.back.model.Metric;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class DiskIOSpecification {

    public static  Specification<DiskIO> byCriteria(DiskIOSearchCriteria criteria) {

        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Join<DiskIO, Metric> metricJoin = root.join("metric");

            if (!criteria.getDriveName().isBlank()) {
                predicates.add(criteriaBuilder.equal(metricJoin.get("driveName"), criteria.getDriveName()));
            }

            predicates.addAll(MetricTimeSpecifications.byMetricTimeCriteria(metricJoin, criteriaBuilder, criteria.getMetricTimeCriteria()));
            predicates.addAll(BaseEntitySpecifications.byBaseCriteria(root, query, criteriaBuilder, criteria.getBaseCriteria()));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
