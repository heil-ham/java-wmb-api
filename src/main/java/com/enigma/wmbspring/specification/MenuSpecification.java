package com.enigma.wmbspring.specification;

import com.enigma.wmbspring.dto.request.SearchMenuRequest;
import com.enigma.wmbspring.entity.Menu;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class MenuSpecification {
    public static Specification<Menu> getSpecification(SearchMenuRequest request) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getName() != null) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("name")), "%" + request.getName().toLowerCase() + "%"
                        )
                );
            }

            if (request.getPrice() != null) {
                predicates.add(
                        criteriaBuilder.equal(
                                root.get("price"), request.getPrice()
                        )
                );
//                predicates.add(
//                        criteriaBuilder.greaterThan(
//                                root.get("price"), request.getMinPrice()
//                        )
//                );
//                predicates.add(
//                        criteriaBuilder.lessThan(
//                                root.get("price"), request.getMaxPrice()
//                        )
//                );
            }
//
            if (request.getMinPrice() != null) {
                predicates.add(
                        criteriaBuilder.greaterThan(
                                root.get("price"), request.getMinPrice()
                        )
                );
            }

            if (request.getMaxPrice() != null) {
                predicates.add(
                        criteriaBuilder.lessThan(
                                root.get("price"), request.getMaxPrice()
                        )
                );
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        });
    }
}
