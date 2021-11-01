package com.techhype.digitalinventory.company;

import com.techhype.digitalinventory.constants.ServerMessage;
import com.techhype.digitalinventory.constants.ServerStatus;
import com.techhype.digitalinventory.middlewares.AuthMiddleware;
import com.techhype.digitalinventory.models.BaseResponse;
import com.techhype.digitalinventory.models.PaginationResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/companies")
public class CompanyController {
    private CompanyService cService;
    private AuthMiddleware authMiddleware;

    @Autowired
    public CompanyController(CompanyService cService, AuthMiddleware authMiddleware) {
        this.cService = cService;
        this.authMiddleware = authMiddleware;
    }

    @GetMapping(path = "{companyid}")
    public ResponseEntity<BaseResponse> getCompanyByCompanyid(@PathVariable String companyid,
            @RequestHeader("Authorization") String authorization) {
        try {
            var auth = authMiddleware.checkToken(authorization);
            if (!auth.isAuth()) {
                return auth.getResponse();
            }
            if (auth.getTokenData().getRole().equals("normaluser")) {
                return new ResponseEntity<BaseResponse>(
                        new BaseResponse(ServerStatus.UNAUTHORIZED, ServerMessage.UNAUTHORIZED, null),
                        HttpStatus.UNAUTHORIZED);
            }
            var company = cService.getCompanyByCompanyid(companyid, auth.getTokenData());
            if (!company.isPresent()) {
                return new ResponseEntity<>(BaseResponse.notFound(), HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok().body(BaseResponse.ok(company));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(BaseResponse.internalServerError());
        }
    }

    @PostMapping
    public ResponseEntity<BaseResponse> addCompany(@RequestBody Company company,
            @RequestHeader("Authorization") String authorization) {
        try {
            var auth = authMiddleware.checkToken(authorization);
            if (!auth.isAuth()) {
                return auth.getResponse();
            }
            if (!auth.getTokenData().getRole().equals("superadmin")) {
                return new ResponseEntity<BaseResponse>(
                        new BaseResponse(ServerStatus.UNAUTHORIZED, ServerMessage.UNAUTHORIZED, null),
                        HttpStatus.UNAUTHORIZED);
            }
            var newcompany = cService.addCompany(company, auth.getTokenData());
            var ref = newcompany.getCompanyid();
            return new ResponseEntity<>(new BaseResponse(ServerStatus.CREATED, ServerMessage.Created(ref), newcompany),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(BaseResponse.internalServerError());
        }
    }

    @PutMapping(path = "{companyid}")
    public ResponseEntity<BaseResponse> updateCompany(@PathVariable String companyid, @RequestBody Company company,
            @RequestHeader("Authorization") String authorization) {
        try {
            var auth = authMiddleware.checkToken(authorization);
            if (!auth.isAuth()) {
                return auth.getResponse();
            }
            if (auth.getTokenData().getRole().equals("normaluser")) {
                return new ResponseEntity<BaseResponse>(
                        new BaseResponse(ServerStatus.UNAUTHORIZED, ServerMessage.UNAUTHORIZED, null),
                        HttpStatus.UNAUTHORIZED);
            }
            var success = cService.updateCompany(companyid, company, auth.getTokenData());
            if (!success) {
                return new ResponseEntity<>(BaseResponse.notFound(), HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok()
                    .body(new BaseResponse(ServerStatus.OK, ServerMessage.Updated(companyid), companyid));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(BaseResponse.internalServerError());
        }
    }

    @DeleteMapping(path = "{companyid}")
    public ResponseEntity<BaseResponse> softDeleteCompany(@PathVariable String companyid,
            @RequestHeader("Authorization") String authorization) {
        try {
            var auth = authMiddleware.checkToken(authorization);
            if (!auth.isAuth()) {
                return auth.getResponse();
            }
            if (!auth.getTokenData().getRole().equals("superadmin")) {
                return new ResponseEntity<BaseResponse>(
                        new BaseResponse(ServerStatus.UNAUTHORIZED, ServerMessage.UNAUTHORIZED, null),
                        HttpStatus.UNAUTHORIZED);
            }
            var success = cService.softDeleteCompany(companyid, auth.getTokenData());

            if (!success) {
                return new ResponseEntity<>(BaseResponse.notFound(), HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(BaseResponse.internalServerError());
        }
    }

    @GetMapping
    public ResponseEntity<BaseResponse> getCompanies(@RequestParam(required = false) String search,
            @RequestParam(defaultValue = "1") int page, @RequestParam int perpage,
            @RequestParam(defaultValue = "createddate") String sortby, @RequestParam(defaultValue = "1") String reverse,
            @RequestHeader("Authorization") String authorization) {
        try {
            var auth = authMiddleware.checkToken(authorization);
            if (!auth.isAuth()) {
                return auth.getResponse();
            }
            if (!auth.getTokenData().getRole().equals("superadmin")) {
                return new ResponseEntity<BaseResponse>(
                        new BaseResponse(ServerStatus.UNAUTHORIZED, ServerMessage.UNAUTHORIZED, null),
                        HttpStatus.UNAUTHORIZED);
            }
            var companypage = cService.getCompanies(search, page, perpage, sortby, reverse, auth.getTokenData());
            var body = new PaginationResponse();
            body.setData(companypage.getContent());
            body.setPagecount(companypage.getTotalPages());
            body.setTotal(companypage.getTotalElements());
            body.setPage(page);
            body.setPerpage(perpage);
            return ResponseEntity.ok().body(body);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(BaseResponse.internalServerError());
        }
    }

}
