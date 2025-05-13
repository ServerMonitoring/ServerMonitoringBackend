package com.example.back.util.criteriaSpecification;

import com.example.back.dto.search.UserSearchCriteria;
import com.example.back.model.Users;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserSpecification {

    public static Specification<Users> byCriteria(UserSearchCriteria criteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getName() != null && !criteria.getName().isBlank()){
                predicates.add(criteriaBuilder.like(root.get("name"),"%"+ criteria.getName() + "%"));
            }
            if (criteria.getSurname() != null && !criteria.getSurname().isBlank()){
                predicates.add(criteriaBuilder.like(root.get("surname"), "%"+ criteria.getSurname()+"%"));
            }
            if (criteria.getPatronymic() != null && !criteria.getPatronymic().isBlank()){
                predicates.add(criteriaBuilder.like(root.get("patronymic"), "%"+ criteria.getPatronymic() +"%"));
            }
            if (criteria.getDepartment() != null && !criteria.getDepartment().isBlank()){
                predicates.add(criteriaBuilder.like(root.get("department"), "%"+ criteria.getDepartment()+"%"));
            }
            if (criteria.getPosition() != null && !criteria.getPosition().isBlank()){
                predicates.add(criteriaBuilder.like(root.get("position"), "%"+ criteria.getPosition()+"%"));
            }
            if (criteria.getLogin() != null && !criteria.getLogin().isBlank()){
                predicates.add(criteriaBuilder.like(root.get("login"), "%"+ criteria.getLogin()+"%"));
            }


            predicates.addAll(BaseEntitySpecifications.byBaseCriteria(root, query, criteriaBuilder, criteria));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
