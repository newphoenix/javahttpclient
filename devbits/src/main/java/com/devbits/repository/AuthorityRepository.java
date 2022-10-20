package com.devbits.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.devbits.entity.Authority;
import com.devbits.entity.AuthorityId;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, AuthorityId> {

	@Query(value="SELECT role from Authority a WHERE a.username = :username",nativeQuery = true)
	List<String> getAuthoritiesByUserName(@Param("username") String username);

}
