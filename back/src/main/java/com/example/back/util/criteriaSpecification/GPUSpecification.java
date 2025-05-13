package com.example.back.util.criteriaSpecification;

import com.example.back.dto.search.GPUSearchCriteria;

import com.example.back.model.GPU;
import com.example.back.model.Metric;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class GPUSpecification {

    public static Specification<GPU> byCriteria(GPUSearchCriteria criteria){

        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Join<GPU, Metric> metricJoin = root.join("metric");

            if (criteria.getGpuName() != null && !criteria.getGpuName().isBlank()){
                predicates.add(criteriaBuilder.like(root.get("gpuName"), "%"+ criteria.getGpuName() +"%"));
            }

            predicates.addAll(MetricTimeSpecifications.byMetricTimeCriteria(metricJoin, criteriaBuilder, criteria.getMetricTimeCriteria()));
            predicates.addAll(BaseEntitySpecifications.byBaseCriteria(root, query, criteriaBuilder, criteria.getBaseCriteria()));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
