package com.microservices.drivenzy.otpservice.otpservice.service;

import com.microservices.drivenzy.otpservice.otpservice.dto.EmpDto;
import com.microservices.drivenzy.otpservice.otpservice.dto.EmployeeOnLeaveDto;
import com.microservices.drivenzy.otpservice.otpservice.modal.Employees;
import com.microservices.drivenzy.otpservice.otpservice.modal.LeaveTracker;
import com.microservices.drivenzy.otpservice.otpservice.repository.LeaveTrackerRepository;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

@Service
public class LeaveTrackerService {

    @Autowired
    private LeaveTrackerRepository leaveTrackerRepository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private EmployeeService employeeService;

    public List<String> findEmployeesOnLeave1(String month, String day, String year) {
        List<LeaveTracker> attendances = leaveTrackerRepository.findByMonthAndYear(month, year);
//        logger.info("Attendance data: " + attendances.toString());
        List<String> employeesOnLeave = new ArrayList<>();
        for (LeaveTracker attendance : attendances) {
            for (Map.Entry<String, Map<String, Integer>> entry : attendance.getEmployeeAttendance().entrySet()) {
                if (entry.getValue().get(day) == 1) {
                    employeesOnLeave.add(entry.getKey());
                }else if(entry.getValue().get(day) == 2){
                    employeesOnLeave.add(entry.getKey());
                }else if(entry.getValue().get(day) == 3){
                    employeesOnLeave.add(entry.getKey());
                }
            }
        }
        return employeesOnLeave;
    }

    public EmployeeOnLeaveDto findEmployeesOnLeaveDetails(String month, String day, String year) {
        List<LeaveTracker> attendances = leaveTrackerRepository.findByMonthAndYear(month, year);
//        logger.info("Attendance data: " + attendances.toString());
        EmployeeOnLeaveDto employeeOnLeaveDto = new EmployeeOnLeaveDto();
        for (LeaveTracker attendance : attendances) {
            for (Map.Entry<String, Map<String, Integer>> entry : attendance.getEmployeeAttendance().entrySet()) {
                EmpDto empDto = new EmpDto();
                empDto.setName(entry.getKey());
                //List<Employees> employees =  employeeService.getEmployeeByEmail(entry.getKey()+"@bajajfinserv.in");
                Employees employees = employeeService.getEmployeeByEmpId(entry.getKey());
                if(!FormatUtils.isNullOrEmpty(employees)){
                    empDto.setName(employees.getName());
                    empDto.setDepartment(employees.getDepartment());
                    empDto.setEmail(employees.getEmail());
                }
                if (entry.getValue().get(day) == 1) {
                    employeeOnLeaveDto.getLeaveEmployees().add(empDto);
                }else if(entry.getValue().get(day) == 2){
                    employeeOnLeaveDto.getWfhEmployees().add(empDto);
                }else if(entry.getValue().get(day) == 3){
                    employeeOnLeaveDto.getOnSiteEmployees().add(empDto);
                }
            }
        }
        logger.info("Employee on leave data: " + employeeOnLeaveDto.toString());
        return employeeOnLeaveDto;
    }

    public EmployeeOnLeaveDto findEmployeesOnLeaveDetails(String month, String day, String year, String department) {
        List<LeaveTracker> attendances = leaveTrackerRepository.findByMonthAndYear(month, year);
//        logger.info("Attendance data: " + attendances.toString());
        EmployeeOnLeaveDto employeeOnLeaveDto = new EmployeeOnLeaveDto();
        for (LeaveTracker attendance : attendances) {
            for (Map.Entry<String, Map<String, Integer>> entry : attendance.getEmployeeAttendance().entrySet()) {
                EmpDto empDto = new EmpDto();
                empDto.setName(entry.getKey());
//                List<Employees> employees =  employeeService.getEmployeeByEmail(entry.getKey()+"@bajajfinserv.in");
                Employees employees = employeeService.getEmployeeByEmpId(entry.getKey());
                if(!FormatUtils.isNullOrEmpty(employees)){
                    empDto.setDepartment(employees.getDepartment());
                    empDto.setEmail(employees.getEmail());
                }
                if(!FormatUtils.isNullOrEmpty(employees) && employees.getDepartment().equals(department)){
                    empDto.setDepartment(employees.getDepartment());
                    empDto.setEmail(employees.getEmail());
                    empDto.setName(employees.getName());
                    if (entry.getValue().get(day) == 1) {
                        employeeOnLeaveDto.getLeaveEmployees().add(empDto);
                    }else if(entry.getValue().get(day) == 2){
                        employeeOnLeaveDto.getWfhEmployees().add(empDto);
                    }else if(entry.getValue().get(day) == 3){
                        employeeOnLeaveDto.getOnSiteEmployees().add(empDto);
                    }
                }
            }
        }
        return employeeOnLeaveDto;
    }

    private static final Logger logger = Logger.getLogger(LeaveTrackerService.class.getName());
    public void saveLeaveTracker(LeaveTracker leaveTracker) {
        try{
            leaveTracker.setId(sequenceGeneratorService.getNextSequence(LeaveTracker.SEQUENCE_NAME).toString());
            leaveTrackerRepository.save(leaveTracker);
        }catch (Exception e){
            logger.info("Error in saving Leave Tracker");
        }
    }

    public void importExcelFile(MultipartFile file, String month, String year) {
        logger.info("Importing Excel file");
        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            LeaveTracker attendance = new LeaveTracker();
            attendance.setMonth(month);
            attendance.setYear(year);
            Map<String, Map<String, Integer>> employeeAttendance = new HashMap<>();

            while (rows.hasNext()) {
                Row currentRow = rows.next();
                if (currentRow.getRowNum() == 0) {
                    // Skip header row
                    continue;
                }

//                String empCode = currentRow.getCell(0).getStringCellValue();
                Cell empCodeCell = currentRow.getCell(0);
                String empCode;

                switch (empCodeCell.getCellType()) {
                    case STRING:
                        empCode = empCodeCell.getStringCellValue();
                        break;
                    case NUMERIC:
                        empCode = String.valueOf((int) empCodeCell.getNumericCellValue());
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid cell type");
                }
                Map<String, Integer> attendanceMap = new HashMap<>();
                for (int i = 2; i < currentRow.getPhysicalNumberOfCells(); i++) {
                    Cell cell = currentRow.getCell(i);
                    int value = getValue(cell);
                    attendanceMap.put(String.valueOf(i - 1), value);
                }

                employeeAttendance.put(empCode, attendanceMap);
            }
            attendance.setEmployeeAttendance(employeeAttendance);
//            logger.info("Attendance data: " + attendance.toString());
            saveLeaveTracker(attendance);
            logger.info("Successfully imported data from Excel file");
        } catch (IOException e) {
            logger.info("Failed to import data from Excel file");
            throw new RuntimeException("Failed to import data from Excel file", e);
        }
    }

    private int getValue(Cell cell) {
        short colorIndex = cell.getCellStyle().getFillForegroundColor();
        int value;
        logger.info("Color index: " + colorIndex);
        if (colorIndex == IndexedColors.RED.getIndex()) {
            value = 1;
        } else if (colorIndex == IndexedColors.ORANGE.getIndex()) {
            value = 2;
        } else if (colorIndex == IndexedColors.BLUE.getIndex()) {
            value = 3;
        } else {
            value = (int) cell.getNumericCellValue();
        }
        return value;
    }
}
