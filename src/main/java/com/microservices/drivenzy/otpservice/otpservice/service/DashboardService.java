package com.microservices.drivenzy.otpservice.otpservice.service;

import com.microservices.drivenzy.otpservice.otpservice.dto.Dashboard;
import com.microservices.drivenzy.otpservice.otpservice.dto.DepartmentLeaveDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
            List<DepartmentLeaveDto> departmentLeaveDtos = leaveTrackerService.findCountOfLeave(LocalDate.now());
            int totalLeaveCount = 0;
            int totalWfhCount = 0;
            int totalOnSiteCount = 0;
            for(DepartmentLeaveDto departmentLeaveDto : departmentLeaveDtos){
                totalLeaveCount += departmentLeaveDto.getLeaveCount();
                totalWfhCount += departmentLeaveDto.getWfhCount();
                totalOnSiteCount += departmentLeaveDto.getOnSiteCount();
            }
            fetchDashboard.setTotalLeaveCount(totalLeaveCount);
            fetchDashboard.setTotalWfhCount(totalWfhCount);
            fetchDashboard.setTotalOnSiteCount(totalOnSiteCount);
            fetchDashboard.setDepartmentLeaveDtosList(departmentLeaveDtos);
            return fetchDashboard;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
