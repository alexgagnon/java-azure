package org.example.pages;

import java.util.Date;
import java.util.List;

import org.example.entities.employees.EmployeeDTO;

import lombok.Data;

@Data
public class HomeModel {
  private Date date;
  private List<EmployeeDTO> employees;
}
