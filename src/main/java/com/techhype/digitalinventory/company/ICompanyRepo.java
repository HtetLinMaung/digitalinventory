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
    @Query("select c from Company c where status = 1 and (companyid = :companyid or companyname = :companyname)")
    Page<Company> findCompany(@Param("companyid") String companyid, @Param("companyname") String companyname,
            Pageable pagable);

    @Query("select c from Company c where status = 1 and (companyid like %:companyid% or companyname like %:companyname%)")
    Page<Company> findCompanyWithContain(@Param("companyid") String companyid, @Param("companyname") String companyname,
            Pageable pagable);

    Page<Company> findByStatus(int status, Pageable pagable);

    Optional<Company> findByStatusAndCompanyid(int status, String companyid);
}
