package com.example.EmployeeDirectoryService.Mapper;

import com.example.EmployeeDirectoryService.EmployeeDTO.EmployeeDTO;
import com.example.EmployeeDirectoryService.Entity.Employees;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeMapper instance = Mappers.getMapper(EmployeeMapper.class);

    @Mapping(source = "name",target = "fullName")
    @Mapping(source = "employeeEmail", target ="email")
    EmployeeDTO toDTO(Employees employees);

    @Mapping(source = "fullName", target = "name")
    @Mapping(source = "email", target = "employeeEmail")
    Employees toEntity(EmployeeDTO employeeDTO);


    List<EmployeeDTO> toDTOList(List<Employees> employeesList);

}
