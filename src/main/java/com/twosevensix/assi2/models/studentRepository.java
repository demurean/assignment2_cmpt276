package com.twosevensix.assi2.models;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface studentRepository extends JpaRepository<student, Integer> {
    List<student> findByWeight(int weight);
    List<student> findByHeight(int height);
    List<student> findByName(String name);
    List<student> findByGpa(double gpa);
    List<student> findBySid(int sid);
    
}
