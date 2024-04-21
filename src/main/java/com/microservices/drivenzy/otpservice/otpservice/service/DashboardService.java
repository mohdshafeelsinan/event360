package com.microservices.drivenzy.otpservice.otpservice.service;

import com.microservices.drivenzy.otpservice.otpservice.dto.Dashboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DashboardService {
    private Dashboard dashboard;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    EventFormService eventFormService;
    @Autowired
    LeaveTrackerService leaveTrackerService;

    public Dashboard getDashboardDetails(){
        Dashboard fetchDashboard = new Dashboard();
        try {
            fetchDashboard.setEmpCount(employeeService.getEmpCount());
            fetchDashboard.setEventCount(eventFormService.getEventCount());
            fetchDashboard.setEmployeesDob(employeeService.getEmployeesOfDob());
            fetchDashboard.setDepartmentLeaveDtosList(leaveTrackerService.findCountOfLeave(LocalDate.now()));
            return fetchDashboard;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
