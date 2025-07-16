package com.google.legal_sales_sidekick.repository;

import com.google.legal_sales_sidekick.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
