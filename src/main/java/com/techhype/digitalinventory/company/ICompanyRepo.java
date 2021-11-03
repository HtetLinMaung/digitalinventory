package com.techhype.digitalinventory.company;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ICompanyRepo extends JpaRepository<Company, Long> {
    @Query("select c from Company c where status = 1 and (companyid = :search or companyname = :search)")
    Page<Company> findCompany(@Param("search") String search, Pageable pagable);

    @Query("select c from Company c where status = 1 and (companyid like %:search% or companyname like %:search%)")
    Page<Company> findCompanyWithContain(@Param("search") String search, Pageable pagable);

    Page<Company> findByStatus(int status, Pageable pagable);

    Optional<Company> findByStatusAndCompanyid(int status, String companyid);
}
