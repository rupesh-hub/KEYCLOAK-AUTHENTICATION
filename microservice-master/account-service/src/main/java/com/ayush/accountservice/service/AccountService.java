package com.ayush.accountservice.service;

import com.ayush.accountservice.proxy.StudentProxyService;
import com.ayush.accountservice.repo.AccountRepo;
import com.ayush.shared.model.Fee;
import com.ayush.shared.model.Student;
import com.ayush.shared.pojo.FeePOJO;
import com.ayush.shared.pojo.RequestPOJO;
import com.ayush.shared.pojo.Response;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {
    private final AccountRepo accountRepo;

    private final StudentProxyService studentProxyService;

    public AccountService(AccountRepo accountRepo, StudentProxyService studentProxyService) {
        this.accountRepo = accountRepo;
        this.studentProxyService = studentProxyService;
    }

    public Fee payFee(RequestPOJO requestPOJO){
        if (requestPOJO.getStudentId()>0){
            Fee fee = requestPOJO.getFee();
            fee.setStudentId(requestPOJO.getStudentId());
             return accountRepo.save(fee);
        }else{
            Student student= Student.builder()
                    .name(requestPOJO.getStudentName())
                    .address(requestPOJO.getAddress())
                    .faculty(requestPOJO.getFaculty())
                    .build();
            Student savedStudent = studentProxyService.save(student);
            Fee fee=requestPOJO.getFee();
            fee.setStudentId(savedStudent.getId());
            return accountRepo.save(fee);

        }
    }

    public List<Fee> getFeeByStudentId(int studentId){
        List<Fee> byStudentId = accountRepo.findByStudentId(studentId);
        return byStudentId;
    }

    public Response getStudentPaidFee(int studentId){
        Student student = studentProxyService.getStudentById(studentId);
        List<Fee> feeList = getFeeByStudentId(studentId);
        List<FeePOJO> feePOJOList=new ArrayList<>();
        for (Fee fee:feeList){
            feePOJOList.add(new FeePOJO(fee.getTitle(),fee.getPaidAmount()));
        }
        Response response=new Response(student.getName(),feePOJOList);
        return response;
    }

    public List<Response> getAllFee() {
        List<Fee> feeList = accountRepo.findAll();
        List<Response> responses=new ArrayList<>();
        for (Fee fee:
             feeList) {
            Response paidFee = getStudentPaidFee(fee.getStudentId());
            responses.add(paidFee);
        }
        return responses;
    }
}
