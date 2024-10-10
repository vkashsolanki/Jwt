package com.jwt.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.ObjectIdGenerators.UUIDGenerator;
import com.jwt.entity.Employee;

@Service
public class EmployeeService {

	private List<Employee> elist = new ArrayList<Employee>();

	public EmployeeService() {

		elist.add(new Employee(UUID.randomUUID().toString(), "ram", "ram@gamila.com"));
		elist.add(new Employee(UUID.randomUUID().toString(), "shyam", "shyam@gamila.com"));

		elist.add(new Employee(UUID.randomUUID().toString(), "krishan", "kirhsan@gamila.com"));
		elist.add(new Employee(UUID.randomUUID().toString(), "gopal", "gopal@gamila.com"));

	}
	
	public List<Employee> getEmplist()
	{
		
		return elist;
		
	}
	

}
