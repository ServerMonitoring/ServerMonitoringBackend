package com.example.back.util.criteriaSpecification;

import com.example.back.dto.search.BaseSearchCriteria;
import jakarta.persistence.criteria.*;

import java.util.ArrayList;
import java.util.List;

public class BaseEntitySpecifications {

    public static <T> List<Predicate> byBaseCriteria(Root<T> root, CriteriaQuery<?> query,
                                                     CriteriaBuilder criteriaBuilder,
                                                     BaseSearchCriteria criteria) {

        List<Predicate> predicates = new ArrayList<>();

        if (criteria.getId() != null){
            predicates.add(criteriaBuilder.equal(root.get("id"), criteria.getId()));
        }
        if (criteria.getDistinct()) {
            query.distinct(true);
        }
        if (criteria.getSortBy() != null && !criteria.getSortBy().isEmpty()) {
            String sortBy = criteria.getSortBy().trim();
            boolean descending = sortBy.startsWith("-");
            if (descending) {
                sortBy = sortBy.substring(1);
            }
            try {
                Order order = descending ? criteriaBuilder.desc(root.get(sortBy)) : criteriaBuilder.asc(root.get(sortBy));
                query.orderBy(order);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid sortBy field: " + sortBy);
            }
        }

        return predicates;
    }
}
