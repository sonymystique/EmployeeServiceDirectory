package com.example.EmployeeDirectoryService.service.impl;
import com.example.EmployeeDirectoryService.auditing.AuditActionContextHolder;
import com.example.EmployeeDirectoryService.employeeDto.EmployeeDTO;
import com.example.EmployeeDirectoryService.entity.Employees;
import com.example.EmployeeDirectoryService.exceptions.DomainNotFoundException;
import com.example.EmployeeDirectoryService.exceptions.EmployeeNotFoundException;
import com.example.EmployeeDirectoryService.exceptions.HttpMessageNotReadableException;
import com.example.EmployeeDirectoryService.exceptions.InvalidInputException;
import com.example.EmployeeDirectoryService.mapper.EmployeeMapper;
import com.example.EmployeeDirectoryService.repository.EmployeeRepository;
import com.example.EmployeeDirectoryService.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static com.example.EmployeeDirectoryService.constants.domain;


// grr
@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * dghg
     * @return
     */
    @Override
    public List<EmployeeDTO> getAllDetails() {
        try {
            List<EmployeeDTO> employeeDTOList = employeeRepository.findAll().stream().map(EmployeeMapper.instance::toDTO).toList();
            if (employeeDTOList.isEmpty()) {
                throw new EmployeeNotFoundException("Database is empty");
            }
            return employeeDTOList;
        } catch (EmployeeNotFoundException ex) {
            log.error("database is empty");
            throw new EmployeeNotFoundException("add some data first");
        }
    }

    /**
     *
     * @param intOffset - fghg
     * @return
     */
    @Override
    public Page<Employees> findEmployeesUsingPaging(int intOffset, int page) {
        try {
            if (intOffset < 0 || page<0) {
                throw new InvalidInputException("input is invalid");
            }
            Page<Employees> employeesPage = employeeRepository.findAll(PageRequest.of(intOffset, page));
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
    public List<EmployeeDTO> getFilteredDetails() {
        try {
            List<EmployeeDTO> filtered;
            log.info("Fetching employee details");
            filtered = employeeRepository.findAll().stream().filter(e -> e.getEmployeeEmail() != null && e.getEmployeeEmail().contains(domain)).map(EmployeeMapper.instance::toDTO).collect(Collectors.toList());
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
    @Transactional(rollbackFor = InvalidInputException.class)
    public EmployeeDTO createEmployee(EmployeeDTO dto) throws InvalidInputException {
        try {

            if (dto == null) {
                throw new InvalidInputException("EmployeeDTO cannot be null");
            }
            AuditActionContextHolder.setAction("CREATE");
            Employees saved = employeeRepository.save(EmployeeMapper.instance.toEntity(dto));
            return EmployeeMapper.instance.toDTO(saved);

        } catch (InvalidInputException ex) {
            log.error("error in input");
            throw new InvalidInputException("Not able to create new employee");
        }catch (DataIntegrityViolationException ex){
            throw new DataIntegrityViolationException("duplicated values");
        }catch(HttpMessageNotReadableException ex){
            throw new HttpMessageNotReadableException("Input cannot be converted to json");
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
            AuditActionContextHolder.setAction("UPDATE");
            employeeRepository.save(employees);
            return EmployeeMapper.instance.toDTO(employees);
        } catch (EmployeeNotFoundException ex) {
            throw new EmployeeNotFoundException("no employee with given id");
        }
    }
    @Override
    public Optional<Employees> deleteEmployees(Long id) {
        try {
            Optional<Employees> employees = employeeRepository.findById(id);
            if (employees.isEmpty()) {
                throw new EmployeeNotFoundException("no employee with given id");
            }
        AuditActionContextHolder.setAction("DELETE");
       employeeRepository.deleteById(id);
            return employees;
        } catch (EmployeeNotFoundException ex) {
            throw new EmployeeNotFoundException("no employee with given id");
        }
    }
}
