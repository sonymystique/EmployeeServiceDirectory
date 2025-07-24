package com.example.EmployeeDirectoryService.service.impl;

import com.example.EmployeeDirectoryService.employeeDto.EmployeeDTO;
import com.example.EmployeeDirectoryService.entity.Employees;
import com.example.EmployeeDirectoryService.exceptions.DomainNotFoundException;
import com.example.EmployeeDirectoryService.exceptions.EmployeeNotFoundException;
import com.example.EmployeeDirectoryService.exceptions.InvalidInputException;
import com.example.EmployeeDirectoryService.mapper.EmployeeMapper;
import com.example.EmployeeDirectoryService.repository.EmployeeRepository;
import com.example.EmployeeDirectoryService.service.EmployeeService;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<EmployeeDTO> getAllDetails() {
        try {
            List<EmployeeDTO> employeeDTOList = employeeRepository.findAll().stream()
                    .map(EmployeeMapper.instance::toDTO)
                    .toList();
            if (employeeDTOList.isEmpty()) {
                throw new EmployeeNotFoundException("Database is empty");
            }
            return employeeDTOList;

        } catch (EmployeeNotFoundException ex) {
            log.error("database is empty");
            throw new EmployeeNotFoundException("add some data first");
        }
    }

    @Override
    public List<EmployeeDTO> getFilteredDetails() {
        List<EmployeeDTO> filtered;
        try {
            log.info("Fetching employee details");
            filtered = employeeRepository.findAll().stream()
                    .filter(e -> e.getEmployeeEmail() != null &&
                            e.getEmployeeEmail().contains("@mycompany.com"))
                    .map(EmployeeMapper.instance::toDTO)
                    .collect(Collectors.toList());
            if (filtered.isEmpty()) {
                throw new DomainNotFoundException("No employee found with the given domain: ");
            }
            return filtered;
        } catch (DomainNotFoundException ex) {
            log.error("Domain not found for the given employee");
            throw new DomainNotFoundException("no employee found with the given domain");
        }
    }

    @Override
    @Transactional
    public EmployeeDTO createEmployee(EmployeeDTO dto) throws InvalidInputException {
        //guard clause
        try {
//            for duplicate create request
//            Employees employeees = EmployeeMapper.instance.toEntity(dto);
//            EmployeeDTO check = employeeRepository.fin
//

            if (dto == null) {
                throw new InvalidInputException("Product cannot be null");
            }
            Employees saved = employeeRepository.save(EmployeeMapper.instance.toEntity(dto));
            return EmployeeMapper.instance.toDTO(saved);

        } catch (InvalidInputException ex) {
            log.error("error in input");
            throw new InvalidInputException("Not able to create new employee");
        }


    }

    @Override
    public List<EmployeeDTO> getDomainByQuery(String domain) {
        try {
            if (domain == null) {
                //guard clause
                throw new InvalidInputException("Domain cannot be null");
            }
            List<Employees> employees = employeeRepository.getEmployeeWithDomain(domain);
            List<EmployeeDTO> employeeDTOList = EmployeeMapper.instance.toDTOList(employees);
            if (employeeDTOList.isEmpty()) {
                throw new EmployeeNotFoundException("no employee found with given domain");
            }
            return employeeDTOList;
        } catch (InvalidInputException ex) {
            log.error("Domain cannot be null");
            throw new InvalidInputException("Domain cannot be null");
        } catch (EmployeeNotFoundException ex) {
            log.error("Employee not found");
            throw new EmployeeNotFoundException("Employee not found");
        }
    }

    @Override
    public List<EmployeeDTO> findEmployeesUsingSort(String field) {
        //guard clause
        try {
            if (field == null) {
                throw new InvalidInputException("field cannot be null");
            }
            List<Employees> employeesList = employeeRepository.findAll(Sort.by(Sort.Direction.DESC, field));
            List<EmployeeDTO> employeeDTOList = EmployeeMapper.instance.toDTOList(employeesList);
            if (employeeDTOList.isEmpty()) {
                throw new EmployeeNotFoundException("no employees found");
            }
            return employeeDTOList;

        } catch (EmployeeNotFoundException ex) {
            throw new EmployeeNotFoundException("no employees found");
        } catch (InvalidInputException ex) {
            throw new InvalidInputException("field cannot be null");
        }
    }

    @Override
    public Page<Employees> findEmployeesUsingPaging(int offset, int pageSize) {
        //guard clause
        try {
            if (offset < 0 || pageSize < 1) {
                throw new InvalidInputException("input is invalid");
            }
            Page<Employees> employeesPage = employeeRepository.findAll(PageRequest.of(offset, pageSize));
            if (employeesPage.isEmpty()) {
                throw new EmployeeNotFoundException("no employee found");
            }
            return employeesPage;
        } catch (InvalidInputException ex) {
            throw new InvalidInputException("input is invalid");
        } catch (EmployeeNotFoundException ex) {
            throw new EmployeeNotFoundException("no employee found");
        }
    }

    @Override
    public EmployeeDTO updateEmployees(Long id, EmployeeDTO employeeDTO) {
        try {
            Optional<Employees> employeesOptional = employeeRepository.findById(id);
            if (employeesOptional.isEmpty()) {
                throw new EmployeeNotFoundException("no employee with given id");
            }
            Employees employees = employeesOptional.get();
            employees.setName(employeeDTO.getFullName());
            employees.setEmployeeEmail(employeeDTO.getEmail());
            employeeRepository.save(employees);
            return EmployeeMapper.instance.toDTO(employees);
        } catch (EmployeeNotFoundException ex) {
            throw new EmployeeNotFoundException("no employee with given id");
        }

    }

    @Override
    public Boolean deleteEmployees(Long id) {
        try {
            Optional employees = employeeRepository.findById(id);
            if (employees.isEmpty()) {
                throw new EmployeeNotFoundException("no employee with given id");
            }
            employeeRepository.deleteById(id);
            return true;
        } catch (EmployeeNotFoundException ex) {
            throw new EmployeeNotFoundException("no employee with given id");
        }


    }


}
