package com.example.TaskPro.Repository;

import com.example.TaskPro.Models.AssignedPersons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignedPersonRepository extends JpaRepository<AssignedPersons, Integer> {
//    public void findPersonByd(int userId);

}
