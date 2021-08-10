package com.smartech.i2019.adaptiveinterviews.repository;

import com.smartech.i2019.adaptiveinterviews.model.UserAuthorities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersAuthoritiesRepository extends JpaRepository<UserAuthorities, Long>,
        JpaSpecificationExecutor<UserAuthorities> {
    UserAuthorities findUserAuthoritiesByUserId(Long id);
}
