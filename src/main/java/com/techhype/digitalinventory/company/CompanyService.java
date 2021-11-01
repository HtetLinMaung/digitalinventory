package com.techhype.digitalinventory.company;

import java.time.LocalDateTime;
import java.util.Optional;

import com.techhype.digitalinventory.models.TokenData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
    private ICompanyRepo cRepo;

    @Autowired
    public CompanyService(ICompanyRepo cRepo) {
        this.cRepo = cRepo;
    }

    public Optional<Company> getCompanyByCompanyid(String companyid, TokenData tokenData) {
        var role = tokenData.getRole();
        switch (role) {
        case "admin":
            return cRepo.findByStatusAndCompanyid(1, tokenData.getCompanyid());
        case "superadmin":
            return cRepo.findByStatusAndCompanyid(1, companyid);
        }
        return Optional.empty();
    }

    public Page<Company> getCompanies(String search, int page, int perpage, String sortby, String reverse,
            TokenData tokenData) {
        var pagable = PageRequest.of(page - 1, perpage,
                Sort.by(reverse.equals("1") ? Sort.Direction.DESC : Sort.Direction.ASC, sortby));

        if (search == null || search.isEmpty()) {
            return cRepo.findByStatus(1, pagable);
        }

        String s = search.trim();
        if (s.startsWith("\"") && s.endsWith("\"")) {
            s = s.replaceAll("\"", "");
            return cRepo.findCompany(s, s, pagable);
        }
        return cRepo.findCompanyWithContain(s, s, pagable);
    }

    public Company addCompany(Company company, TokenData tokenData) {
        var now = LocalDateTime.now();
        company.setId(null);
        company.setCreateddate(now);
        company.setModifieddate(now);
        var newcompany = cRepo.save(company);
        newcompany.setCompanyid(String.format("C%06d", newcompany.getId()));
        return cRepo.save(newcompany);
    }

    public boolean updateCompany(String companyid, Company company, TokenData tokenData) {
        var role = tokenData.getRole();
        var cid = companyid;
        if (role.equals("admin")) {
            cid = tokenData.getCompanyid();
        }
        var data = getCompanyByCompanyid(cid, tokenData);
        if (!data.isPresent()) {
            return false;
        }
        var old = data.get();
        old.setCompanyname(company.getCompanyname());
        old.setModifieddate(LocalDateTime.now());
        cRepo.save(old);
        return true;
    }

    public boolean softDeleteCompany(String companyid, TokenData tokenData) {
        var data = getCompanyByCompanyid(companyid, tokenData);
        if (!data.isPresent()) {
            return false;
        }
        var old = data.get();
        old.setStatus(0);
        old.setModifieddate(LocalDateTime.now());
        cRepo.save(old);
        return true;
    }

}
