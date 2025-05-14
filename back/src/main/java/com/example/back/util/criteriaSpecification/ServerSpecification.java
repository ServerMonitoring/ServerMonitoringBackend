package com.example.back.util.criteriaSpecification;

import com.example.back.dto.search.ServerSearchCriteria;
import com.example.back.model.Server;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ServerSpecification {

    public static Specification<Server> byCriteria(ServerSearchCriteria criteria) {

        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getUserId() != null){
                predicates.add(criteriaBuilder.equal(root.get("users").get("userId"), criteria.getUserId()));
            }
            if (criteria.getHostname() != null && !criteria.getHostname().isBlank()){
                predicates.add(criteriaBuilder.like(root.get("hostname"), "%"+criteria.getHostname()+"%"));
            }
            if (criteria.getOsInfo() != null && !criteria.getOsInfo().isBlank()){
                predicates.add(criteriaBuilder.like(root.get("osInfo"), "%"+criteria.getOsInfo()+"%"));
            }
            if (criteria.getAddress() != null && !criteria.getAddress().isBlank()){
                predicates.add(criteriaBuilder.like(root.get("address"), "%"+criteria.getAddress()+"%"));
            }
            if (criteria.getAddInfo() != null && !criteria.getAddInfo().isEmpty()){
                predicates.add(criteriaBuilder.like(root.get("addInfo"), "%"+criteria.getAddInfo()+"%"));
            }
            if (criteria.getOnline() != null){
                predicates.add(criteriaBuilder.equal(root.get("online"), criteria.getOnline()));
            }
            if (criteria.getCpuModel() != null && !criteria.getCpuModel().isBlank()){
                predicates.add(criteriaBuilder.like(root.get("cpuModel"), "%"+criteria.getCpuModel()+"%"));
            }
            if (criteria.getCpuCountCores() != null){
                predicates.add(criteriaBuilder.equal(root.get("cpuCountCores"), criteria.getCpuCountCores()));
            }
            if (criteria.getCpuCountCoresPhysical() != null){
                predicates.add(criteriaBuilder.equal(root.get("cpuCountCoresPhysical"), criteria.getCpuCountCoresPhysical()));
            }
            if (criteria.getMinFreq() != null){
                predicates.add(criteriaBuilder.equal(root.get("minFreq"), criteria.getMinFreq()));
            }
            if (criteria.getMaxFreq() != null){
                predicates.add(criteriaBuilder.equal(root.get("maxFreq"), criteria.getMaxFreq()));
            }

            predicates.addAll(BaseEntitySpecifications.byBaseCriteria(root, query, criteriaBuilder, criteria));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
