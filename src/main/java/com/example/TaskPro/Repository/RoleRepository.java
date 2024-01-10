package com.example.TaskPro.Repository;

import com.example.TaskPro.Models.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD
import org.springframework.stereotype.Repository;
=======
>>>>>>> 4aa7db81f3aa327f2c3c05a2b7fa192bb183d725

import javax.management.relation.Role;
import java.util.Optional;

<<<<<<< HEAD
@Repository
=======
>>>>>>> 4aa7db81f3aa327f2c3c05a2b7fa192bb183d725
public interface RoleRepository extends JpaRepository<Roles, Integer> {
    Optional<Roles> findByName(String name);
}
