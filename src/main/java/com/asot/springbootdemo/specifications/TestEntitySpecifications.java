package com.asot.springbootdemo.specifications;

import com.asot.springbootdemo.model.TestEntity;
import org.springframework.data.jpa.domain.Specification;

public class TestEntitySpecifications {
    public static Specification<TestEntity> nameContains(String name) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }
}
