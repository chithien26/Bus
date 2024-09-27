package com.lct.bus.repository;

import com.lct.bus.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u WHERE " +
            "u.username LIKE %:username%")
    List<User> getAllUserByUsername(String username);

//

    Optional<User> findByUsername(String username);
}
