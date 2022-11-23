package com.midorimart.managementsystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.midorimart.managementsystem.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public Optional<User> findByEmail(String email);
    public Optional<User> findByVerificationCode(String verificationCode);
    public List<User> findAllByRoleId(int i);
    @Query(value ="select * from [User] where email Like ?1 or full_name LIKE ?1 or phone_number LIKE ?1 order by id asc OFFSET ?2 ROWS FETCH NEXT ?3 ROWS ONLY" , nativeQuery=true)
    public List<User> findByNameOrEmailOrPhoneNumber(String keyword, int offset, int limit);
}
