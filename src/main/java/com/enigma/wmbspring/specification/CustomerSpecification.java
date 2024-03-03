package com.enigma.wmbspring.specification;

import com.enigma.wmbspring.dto.request.SearchCustomerRequest;
import com.enigma.wmbspring.entity.Customer;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
public class CustomerSpecification {
    public static Specification<Customer> getSpecification(SearchCustomerRequest request) {
        return ((root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (request.getName() != null) {
                Predicate namePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + request.getName().toLowerCase() + "%");
                predicates.add(namePredicate);
            }

            if (request.getPhone_number() != null) {
                Predicate phonePredicate = criteriaBuilder.equal(root.get("phone_number"), request.getPhone_number());
                predicates.add(phonePredicate);
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        });
    }
}
