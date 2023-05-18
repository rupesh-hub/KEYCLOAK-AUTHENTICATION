package com.ayush.accountservice.controller;

import com.ayush.accountservice.service.AccountService;
import com.ayush.shared.controller.BaseController;
import com.ayush.shared.model.Fee;
import com.ayush.shared.pojo.RequestPOJO;
import com.ayush.shared.pojo.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("account")
public class AccountController extends BaseController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{studentId}")
    public ResponseEntity getPaidFee(@PathVariable("studentId") int studentId){
        Response studentPaidFee = accountService.getStudentPaidFee(studentId);
        return ResponseEntity.ok(successResponse("Data Fetched Successfully",studentPaidFee));
    }


    @PostMapping
    public ResponseEntity pay(@RequestBody RequestPOJO requestPOJO){
        Fee paidFee = accountService.payFee(requestPOJO);
        return ResponseEntity.ok(successResponse("Data Saved Successfully",paidFee));
    }

    @GetMapping
    public ResponseEntity all(){
        List<Response> list = accountService.getAllFee();
        return ResponseEntity.ok(successResponse("Data Fetched Successfully",list));
    }

}
