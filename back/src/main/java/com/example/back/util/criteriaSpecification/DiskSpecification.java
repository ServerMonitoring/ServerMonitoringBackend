package com.example.back.util.criteriaSpecification;

import com.example.back.dto.search.DiskSearchCriteria;
import com.example.back.model.Disk;

import com.example.back.model.Metric;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class DiskSpecification {

    public static Specification<Disk> byCriteria(DiskSearchCriteria criteria){

        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Join<Disk, Metric> metricJoin = root.join("metric");

            if (!criteria.getDevice().isBlank()){
                predicates.add(criteriaBuilder.like(root.get("device"), "%"+ criteria.getDevice() +"%"));
            }
            if (!criteria.getMountpoint().isBlank()){
                predicates.add(criteriaBuilder.like(root.get("mountpoint"), "%"+ criteria.getMountpoint() +"%"));
            }

            predicates.addAll(MetricTimeSpecifications.byMetricTimeCriteria(metricJoin, criteriaBuilder, criteria.getMetricTimeCriteria()));
            predicates.addAll(BaseEntitySpecifications.byBaseCriteria(root, query, criteriaBuilder, criteria.getBaseCriteria()));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
