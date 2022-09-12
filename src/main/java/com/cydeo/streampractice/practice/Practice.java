package com.cydeo.streampractice.practice;

import com.cydeo.streampractice.model.*;
import com.cydeo.streampractice.service.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class Practice {

    public static CountryService countryService;
    public static DepartmentService departmentService;
    public static EmployeeService employeeService;
    public static JobHistoryService jobHistoryService;
    public static JobService jobService;
    public static LocationService locationService;
    public static RegionService regionService;

    public Practice(CountryService countryService, DepartmentService departmentService,
                    EmployeeService employeeService, JobHistoryService jobHistoryService,
                    JobService jobService, LocationService locationService,
                    RegionService regionService) {

        Practice.countryService = countryService;
        Practice.departmentService = departmentService;
        Practice.employeeService = employeeService;
        Practice.jobHistoryService = jobHistoryService;
        Practice.jobService = jobService;
        Practice.locationService = locationService;
        Practice.regionService = regionService;

    }

    // You can use the services above for all the CRUD (create, read, update, delete) operations.
    // Above services have all the required methods.
    // Also, you can check all the methods in the ServiceImpl classes inside the service.impl package, they all have explanations.

    // Display all the employees
    public static List<Employee> getAllEmployees() {
        return employeeService.readAll();
    }

    // Display all the countries
    public static List<Country> getAllCountries() {

        return countryService.readAll();
    }

    // Display all the departments
    public static List<Department> getAllDepartments() {
        //TODO Implement the method
        return departmentService.readAll();
    }

    // Display all the jobs
    public static List<Job> getAllJobs() {
        //TODO Implement the method
        return jobService.readAll();

    }

    // Display all the locations
    public static List<Location> getAllLocations() {
        //TODO Implement the method
        return locationService.readAll();

    }

    // Display all the regions
    public static List<Region> getAllRegions() {
        //TODO Implement the method
        return regionService.readAll();
    }

    // Display all the job histories
    public static List<JobHistory> getAllJobHistories() {
        //TODO Implement the method
        return jobHistoryService.readAll();
    }

    // Display all the employees' first names
    public static List<String> getAllEmployeesFirstName() {
        //TODO Implement the method
        return employeeService.readAll().stream()
                .map(Employee::getFirstName)
                .collect(Collectors.toList());

    }

    // Display all the countries' names
    public static List<String> getAllCountryNames() {
        //TODO Implement the method
        return countryService.readAll().stream()
                .map(Country::getCountryName)
                .collect(Collectors.toList());

    }

    // Display all the departments' managers' first names
    public static List<String> getAllDepartmentManagerFirstNames() {
        //TODO Implement the method
        return departmentService.readAll().stream()
                .map(p -> p.getManager().getFirstName())
                .collect(Collectors.toList());

    }

    // Display all the departments where manager name of the department is 'Steven'
    public static List<Department> getAllDepartmentsWhichManagerFirstNameIsSteven() {
        //TODO Implement the method
        return departmentService.readAll()
                .stream().filter(p -> p.getManager().getFirstName().equals("Steven"))
                .collect(Collectors.toList());

    }

    // Display all the departments where postal code of the location of the department is '98199'
    public static List<Department> getAllDepartmentsWhereLocationPostalCodeIs98199() {
        //TODO Implement the method

              return  departmentService.readAll().stream()
                        .filter(department -> department.getLocation().getPostalCode().equals("98199"))
                        .collect(Collectors.toList());

    }

    // Display the region of the IT department
    public static Region getRegionOfITDepartment() throws Exception {
        //TODO Implement the method

       return departmentService.readAll().stream()
                    .filter(p -> p.getDepartmentName().equals("IT"))
                    //.map(p -> p.getLocation().getCountry().getRegion())
                    .findAny().orElseThrow(()-> new Exception("No IT department")).getLocation().getCountry().getRegion();

    }

    // Display all the departments where the region of department is 'Europe'
    public static List<Department> getAllDepartmentsWhereRegionOfCountryIsEurope() {
        //TODO Implement the method
        return departmentService.readAll().stream()
                .filter(p -> p.getLocation().getCountry().getRegion().getRegionName().equals("Europe"))
                .collect(Collectors.toList());

    }

    // Display if there is any employee with salary less than 1000. If there is none, the method should return true
    public static boolean checkIfThereIsNoSalaryLessThan1000() {
        //TODO Implement the method

        return getAllEmployees().stream()
                .noneMatch(p -> p.getSalary() < 1000); //nonMatch method instead of !AnyMatch
                //.all(p->p.getSalary()>1000);
    }

    // Check if the salaries of all the employees in IT department are greater than 2000 (departmentName: IT)
    public static boolean checkIfThereIsAnySalaryGreaterThan2000InITDepartment() {
        //TODO Implement the method
     return employeeService.readAll()
                .stream()
                .filter(p -> p.getDepartment().getDepartmentName().equals("IT"))
               // .allMatch(p -> p.getSalary() > 2000);
             .noneMatch(p -> p.getSalary() < 2000);

     //.filter

    }

    // Display all the employees whose salary is less than 5000
    public static List<Employee> getAllEmployeesWithLessSalaryThan5000() {
        //TODO Implement the method
        return employeeService.readAll().stream()
                .filter(p -> p.getSalary() < 5000)
                .collect(Collectors.toList());

    }

    // Display all the employees whose salary is between 6000 and 7000
    public static List<Employee> getAllEmployeesSalaryBetween() {
        return employeeService.readAll().stream()
                .filter(p -> p.getSalary() < 7000L && p.getSalary() > 6000)
                .collect(Collectors.toList());


    }

    // Display the salary of the employee Grant Douglas (lastName: Grant, firstName: Douglas)
    public static Long getGrantDouglasSalary() throws Exception {
        //TODO Implement the method


            return employeeService.readAll().stream()
                    .filter(p -> p.getFirstName().equals("Douglas") && p.getLastName().equals("Grant"))
                    .findAny().get().getSalary();

    }

    // Display the maximum salary an employee gets
    public static Long getMaxSalary() throws Exception {


           return employeeService.readAll().stream()
                    .sorted(Comparator.comparing(Employee::getSalary).reversed())
                    .findFirst().get().getSalary();

           //.map(p->p.getSalary())
        //.max(Long::compare).get();

    }

    // Display the employee(s) who gets the maximum salary 20
    public static List<Employee> getMaxSalaryEmployee(){


    List<Employee> maxSalaryEmployee = employeeService.readAll().stream()
            .filter(employee -> {
                try {
                    return employee.getSalary().equals(getMaxSalary());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            })
            .collect(Collectors.toList());
    return maxSalaryEmployee;

    }

    // Display the max salary employee's job
    public static Job getMaxSalaryEmployeeJob() throws Exception {


        //TODO Implement the method
        return employeeService.readAll().stream()
                .filter(p -> {
                    try {
                        return p.getSalary().equals(getMaxSalary());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .findAny().get().getJob();

    }

    // Display the max salary in Americas Region
    public static Long getMaxSalaryInAmericasRegion() throws Exception {
        //TODO Implement the method

        return employeeService.readAll().stream()
                .sorted(Comparator.comparing(Employee::getSalary).reversed())
                .filter(p -> p.getDepartment().getLocation().getCountry().getRegion().getRegionName().equals("Americas"))
                .findFirst()
                .get().getSalary();

    }

    // Display the second maximum salary an employee gets
    public static Long getSecondMaxSalary() throws Exception {
        //TODO Implement the method
        return employeeService.readAll().stream()

                .sorted(Comparator.comparing(Employee::getSalary).reversed())
                .skip(1)
                .findFirst().get().getSalary();

    }

    // Display the employee(s) who gets the second maximum salary
    public static List<Employee> getSecondMaxSalaryEmployee() {



       List<Employee> secondMaxSalaryEmployee= employeeService.readAll().stream()
                .filter(p -> {
                    try {
                        return p.getSalary().equals(getSecondMaxSalary());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());

return  secondMaxSalaryEmployee;
    }

    // Display the minimum salary an employee gets
    public static Long getMinSalary() throws Exception {
        //TODO Implement the method

        return employeeService.readAll().stream()
                .sorted(Comparator.comparing(Employee::getSalary))
                .findFirst().get().getSalary();

    }

    // Display the employee(s) who gets the minimum salary
    public static List<Employee> getMinSalaryEmployee() {

        Long minSalary = employeeService.readAll().stream()
                .sorted(Comparator.comparing(Employee::getSalary)).findFirst().get().getSalary();

        return employeeService.readAll().stream()
                .sorted(Comparator.comparing(Employee::getSalary))
                .filter(p -> p.getSalary().equals(minSalary))
                .collect(Collectors.toList());
    }

    // Display the second minimum salary an employee gets
    public static Long getSecondMinSalary() throws Exception {

        return employeeService.readAll().stream()
                .sorted(Comparator.comparing(Employee::getSalary))
                .skip(1).findFirst().get().getSalary();
    }

    // Display the employee(s) who gets the second minimum salary
    public static List<Employee> getSecondMinSalaryEmployee() {

        Long secMinSal = employeeService.readAll().stream()
                .sorted(Comparator.comparing(Employee::getSalary)).skip(1).findFirst().get().getSalary();
        return employeeService.readAll().stream()
                .sorted(Comparator.comparing(Employee::getSalary))
                .filter(p -> p.getSalary().equals(secMinSal))
                .collect(Collectors.toList());

    }

    // Display the average salary of the employees
    public static Double getAverageSalary() {
        //TODO Implement the method
        return employeeService.readAll().stream()
                .collect(Collectors.averagingLong(Employee::getSalary));

    }

    // Display all the employees who are making more than average salary
    public static List<Employee> getAllEmployeesAboveAverage() {
        //TODO Implement the method


        return employeeService.readAll().stream()
                .filter(p -> p.getSalary() > getAverageSalary())
                .collect(Collectors.toList());

    }

    // Display all the employees who are making less than average salary
    public static List<Employee> getAllEmployeesBelowAverage() {
        Double avgSalary = employeeService.readAll().stream()
                .collect(Collectors.averagingLong(Employee::getSalary));

        return employeeService.readAll().stream()
                .filter(p -> p.getSalary() < avgSalary)
                .collect(Collectors.toList());
    }

    // Display all the employees separated based on their department id number
    public static Map<Long, List<Employee>> getAllEmployeesForEachDepartment() {
        return employeeService.readAll().stream()
                .collect(Collectors.groupingBy(employee -> employee.getDepartment().getId()));
    }

    // Display the total number of the departments
    public static Long getTotalDepartmentsNumber() {
        return departmentService.readAll().stream()
                .map(Department::getId).count();

    }

    // Display the employee whose first name is 'Alyssa' and manager's first name is 'Eleni' and department name is 'Sales'
    public static Employee getEmployeeWhoseFirstNameIsAlyssaAndManagersFirstNameIsEleniAndDepartmentNameIsSales() throws Exception {
        //TODO Implement the method
        return employeeService.readAll().stream().filter(p -> p.getFirstName().equals("Alyssa"))
                .filter(p -> p.getManager().getFirstName().equals("Eleni"))
                .filter(p -> p.getDepartment().getDepartmentName().equals("Sales"))
                .findAny().get();

    }

    // Display all the job histories in ascending order by start date
    public static List<JobHistory> getAllJobHistoriesInAscendingOrder() {
        return jobHistoryService.readAll().stream()
                .sorted(Comparator.comparing(JobHistory::getStartDate))
                .collect(Collectors.toList());

    }

    // Display all the job histories in descending order by start date
    public static List<JobHistory> getAllJobHistoriesInDescendingOrder() {
        return jobHistoryService.readAll().stream()
                .sorted(Comparator.comparing(JobHistory::getStartDate).reversed())
                .collect(Collectors.toList());
    }

    // Display all the job histories where the start date is after 01.01.2005
    public static List<JobHistory> getAllJobHistoriesStartDateAfterFirstDayOfJanuary2005() {
        return jobHistoryService.readAll().stream()
                .filter(p -> p.getStartDate().isAfter(LocalDate.of(2005, 01, 01)))
                .collect(Collectors.toList());
    }

    // Display all the job histories where the end date is 31.12.2007 and the job title of job is 'Programmer'
    public static List<JobHistory> getAllJobHistoriesEndDateIsLastDayOfDecember2007AndJobTitleIsProgrammer() {
        return jobHistoryService.readAll().stream()
                .filter(p -> p.getEndDate().isEqual(LocalDate.of(2007, 12, 31)))
                .filter(p -> p.getJob().getJobTitle().equals("Programmer"))
                .collect(Collectors.toList());

    }

    // Display the employee whose job history start date is 01.01.2007 and job history end date is 31.12.2007 and department's name is 'Shipping'
    public static Employee getEmployeeOfJobHistoryWhoseStartDateIsFirstDayOfJanuary2007AndEndDateIsLastDayOfDecember2007AndDepartmentNameIsShipping() throws Exception {
        return jobHistoryService.readAll().stream()
                .filter(p -> p.getStartDate().isEqual(LocalDate.of(2007, 01, 01)))
                .filter(p -> p.getEndDate().isEqual(LocalDate.of(2007, 12, 31)))
                .filter(p -> p.getDepartment().getDepartmentName().equals("Shipping"))
                .findAny().get().getEmployee();
    }

    // Display all the employees whose first name starts with 'A'
    public static List<Employee> getAllEmployeesFirstNameStartsWithA() {
        return employeeService.readAll().stream()
                .filter(p -> p.getFirstName().substring(0, 1).equals("A"))
                .collect(Collectors.toList());
    }

    // Display all the employees whose job id contains 'IT'
    public static List<Employee> getAllEmployeesJobIdContainsIT() {
        return employeeService.readAll().stream()
                .filter(p -> p.getJob().getId().contains("IT"))
                .collect(Collectors.toList());
    }

    // Display the number of employees whose job title is programmer and department name is 'IT'
    public static Long getNumberOfEmployeesWhoseJobTitleIsProgrammerAndDepartmentNameIsIT() {
        return employeeService.readAll().stream()
                .filter(p -> p.getJob().getJobTitle().equals("Programmer"))
                .filter(p -> p.getDepartment().getDepartmentName().equals("IT"))
                .count();
    }

    // Display all the employees whose department id is 50, 80, or 100
    public static List<Employee> getAllEmployeesDepartmentIdIs50or80or100() {
        return employeeService.readAll().stream()
                .filter(employee -> employee.getDepartment().getId().equals(50L)
                        || employee.getDepartment().getId().equals(80L)
                        || employee.getDepartment().getId().equals(100L))
                .collect(Collectors.toList());

    }

    // Display the initials of all the employees
    // Note: You can assume that there is no middle name
    public static List<String> getAllEmployeesInitials() {
        return employeeService.readAll().stream()
                .map(p -> p.getFirstName().substring(0, 1) + p.getLastName().substring(0, 1))
                .collect(Collectors.toList());

    }

    // Display the full names of all the employees
    public static List<String> getAllEmployeesFullNames() {
        return employeeService.readAll().stream()
                .map(p -> p.getFirstName() + " " + p.getLastName())
                .collect(Collectors.toList());
    }

    // Display the length of the longest full name(s)
    public static Integer getLongestNameLength() throws Exception {
        return employeeService.readAll().stream()
                .map(p -> p.getFirstName() + " " + p.getLastName())
                .sorted(Comparator.comparing(String::length).reversed())
                .findFirst().get().length();
    }

    // Display the employee(s) with the longest full name(s)
    public static List<Employee> getLongestNamedEmployee() {
        int longest = employeeService.readAll().stream()
                .map(p -> p.getFirstName() + p.getLastName())
                .sorted(Comparator.comparing(String::length).reversed())
                .findFirst().get().length();
        return employeeService.readAll().stream()
                .filter(p -> (p.getFirstName().length() + p.getLastName().length()) == longest)
                .collect(Collectors.toList());


    }

    // Display all the employees whose department id is 90, 60, 100, 120, or 130
    public static List<Employee> getAllEmployeesDepartmentIdIs90or60or100or120or130() {
        return employeeService.readAll().stream()
                .filter(employee -> employee.getDepartment().getId().equals(90L)
                        || employee.getDepartment().getId().equals(60L)
                        || employee.getDepartment().getId().equals(120L)
                        || employee.getDepartment().getId().equals(130L)
                        || employee.getDepartment().getId().equals(100L))
                .collect(Collectors.toList());
    }

    // Display all the employees whose department id is NOT 90, 60, 100, 120, or 130
    public static List<Employee> getAllEmployeesDepartmentIdIsNot90or60or100or120or130() {
        return employeeService.readAll().stream()
                .filter(employee -> !employee.getDepartment().getId().equals(90L)
                        && !employee.getDepartment().getId().equals(60L)
                        && !employee.getDepartment().getId().equals(120L)
                        && !employee.getDepartment().getId().equals(130L)
                        && !employee.getDepartment().getId().equals(100L))
                .collect(Collectors.toList());

    }
}
