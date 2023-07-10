package com.example.employee.controller;

import com.example.employee.enumConstant.Gender;
import com.example.employee.model.Employee;
import com.example.employee.properties.PropertySourceConfig;
import com.example.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@CrossOrigin(origins = "*")
@ApiIgnore
public class EmployeeController {
    private EmployeeService employeeService;
    private PropertySourceConfig propertySourceConfig;

    public EmployeeController(@Qualifier("emp") EmployeeService employeeService, PropertySourceConfig propertySourceConfig) {
        this.employeeService = employeeService;
        this.propertySourceConfig = propertySourceConfig;
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/logout")
    public String logout(){
        return "redirect:/login?logout";
    }

    @GetMapping("/")
    public String getAllEmployees(
            @RequestParam("pageNo") Optional<Integer> pageNo,
            @RequestParam("pageSize") Optional<Integer> pageSize,
            Model model){
        Page<Employee> page = employeeService.getAllEmployeesPaginated(pageNo.orElse(propertySourceConfig.getPageNo())-1,pageSize.orElse(propertySourceConfig.getPageSize()));
        model.addAttribute("page", page);
        return "index";
    }

    @GetMapping("/showAddEmployeeForm")
    public String showAddEmployeeForm(Model model){
        model.addAttribute("employee",new Employee());
        model.addAttribute("genderEnum", Gender.values());
        return "saveEmployee";
    }

    @PostMapping("/saveEmployeeDetails")
    public String saveEmployeeDetails(@Valid @ModelAttribute("employee") Employee employee, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("genderEnum", Gender.values());
            return "saveEmployee";
        }
        employeeService.saveEmployeeDetails(employee);
        return "redirect:/";
    }

    @GetMapping("/showUpdateEmployeeForm/{id}")
    public String showUpdateEmployeeForm(@PathVariable("id") long id, Model model){
        Employee employee = employeeService.findEmployeeById(id);
        model.addAttribute("employee", employee);
        return "updateEmployee";
    }

    @GetMapping("/removeEmployeeDetails/{id}")
    public String removeEmployeeDetails(@PathVariable("id") long id){
        employeeService.removeEmployeeDetails(id);
        return "redirect:/";
    }

    @GetMapping("/showSearchForm")
    public String searchEmployeeForm(Model model){
        model.addAttribute("employeeList", new Employee());
        return "searchEmployee";
    }

    @GetMapping("/searchEmployeeDetails")
    public String searchEmployeeByFirstName(@Valid @RequestParam("firstName") String firstName, Model model){
        List<Employee> employees = employeeService.findEmployeeByFirstName(firstName);
        model.addAttribute("employeeList",employees);
       return "searchEmployee";
    }
}
