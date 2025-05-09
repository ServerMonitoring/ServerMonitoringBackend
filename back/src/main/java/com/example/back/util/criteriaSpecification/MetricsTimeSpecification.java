package com.example.back.util.criteriaSpecification;

import com.example.back.dto.search.MetricsTimeSearchCriteria;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

public class MetricsTimeSpecification {

    public static <T> List<Predicate> byBaseCriteria(Root<T> root, CriteriaQuery<?> query,
                                                     CriteriaBuilder criteriaBuilder,
                                                     MetricsTimeSearchCriteria criteria) {

        List<Predicate> predicates = new ArrayList<>();

        if (criteria.getStartTime() != null){
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("timestamp"), criteria.getStartTime()));
        }
        if (criteria.getEndTime() != null){
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("timestamp"), criteria.getEndTime()));
        }

        return predicates;
    }
}
