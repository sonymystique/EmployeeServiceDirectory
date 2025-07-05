package com.example.EmployeeDirectoryService.Service.Impl;

import com.example.EmployeeDirectoryService.EmployeeDTO.EmployeeDTO;
import com.example.EmployeeDirectoryService.Entity.Employees;
import com.example.EmployeeDirectoryService.Exceptions.DomainNotFoundException;
import com.example.EmployeeDirectoryService.Mapper.EmployeeMapper;
import com.example.EmployeeDirectoryService.Repository.EmployeeRepository;
import com.example.EmployeeDirectoryService.Service.EmployeeServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;
import java.util.stream.Collectors;
@Service
public class EmployeeService implements EmployeeServiceInterface {

    @Autowired
    private EmployeeRepository employeeRepository;
//    @Autowired
//    private EmployeeMapper employeeMapper;

    public List<EmployeeDTO> getAllDetails() {
    return employeeRepository.findAll().stream()
            .map(EmployeeMapper.instance::toDTO)
            .collect(Collectors.toList());
    }

    public List<EmployeeDTO> getFilteredDetails(String domain){
        List<EmployeeDTO> filtered = employeeRepository.findAll().stream()
            .filter(e ->e.getEmployeeEmail()!=null &&
                    e.getEmployeeEmail().contains(domain))
                .map(EmployeeMapper.instance::toDTO)
                .collect(Collectors.toList());
        if(filtered.isEmpty()){
            throw new
                    DomainNotFoundException("No employee found with the given domain: "+domain);

        }
        return filtered;
    }

    public EmployeeDTO createEmployee(EmployeeDTO dto){
        Employees entity = EmployeeMapper.instance.toEntity(dto);
        Employees saved = employeeRepository.save(entity);
        return EmployeeMapper.instance.toDTO(saved);

    }



}
