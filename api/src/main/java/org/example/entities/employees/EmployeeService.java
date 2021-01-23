package org.example.entities.employees;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
  private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);

  @Autowired
  private EmployeeRepository employeeRepository;

  @Autowired
  private EmployeeMapper employeeMapper;

  public List<EmployeeDTO> findAll() {
    List<EmployeeDTO> list = employeeRepository.findAll().stream().map(x -> {
      log.debug(x.toString());
      return employeeMapper.employeeEntityToEmployeeDTO(x);
    }).collect(Collectors.toList());

    list.forEach(x -> log.debug(x.toString()));

    return list;
  }

  public EmployeeDTO findEmployeeByID(Long id) {
    return employeeMapper.employeeEntityToEmployeeDTO(employeeRepository.findById(id).orElse(null));
  }

  public EmployeeDTO addEmployee(EmployeeDTO employeeDto) {
    return employeeMapper
        .employeeEntityToEmployeeDTO(employeeRepository.save(employeeMapper.employeeDTOToEmployeeEntity(employeeDto)));
  }

  // convert all DTOs to Entities, insert them, then convert the returned
  // entities back to DTOs to send back
  public List<EmployeeDTO> addEmployees(List<EmployeeDTO> employeeDtos) {
    return employeeRepository
        .saveAll(
            employeeDtos.stream().map(x -> employeeMapper.employeeDTOToEmployeeEntity(x)).collect(Collectors.toList()))
        .stream().map(x -> employeeMapper.employeeEntityToEmployeeDTO(x)).collect(Collectors.toList());
  }

  public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDto) {

    Optional<EmployeeEntity> employee = employeeRepository.findById(id);

    // some merge operation between employeeDto and the one in the DB...

    return employeeMapper.employeeEntityToEmployeeDTO(employeeRepository.save(employee.orElse(null)));
  }

  public boolean deleteEmployee(Long id) {
    Optional<EmployeeEntity> employee = employeeRepository.findById(id);
    if (employee.isPresent()) {
      employeeRepository.deleteById(id);
      return true;
    }
    return false;
  }

}
