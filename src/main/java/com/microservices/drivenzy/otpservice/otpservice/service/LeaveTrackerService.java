package com.microservices.drivenzy.otpservice.otpservice.service;

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

    public List<String> findEmployeesOnLeave1(String month, String day, String year) {
        List<LeaveTracker> attendances = leaveTrackerRepository.findByMonthAndYear(month, year);
//        logger.info("Attendance data: " + attendances.toString());
        List<String> employeesOnLeave = new ArrayList<>();
        for (LeaveTracker attendance : attendances) {
            for (Map.Entry<String, Map<String, Integer>> entry : attendance.getEmployeeAttendance().entrySet()) {
                if (entry.getValue().get(day) == 1) {
                    employeesOnLeave.add(entry.getKey());
                }
            }
        }
        return employeesOnLeave;
    }

    public List<String> findEmployeesOnLeave(String month, String day, String year) {
        Query query = new Query();
        query.addCriteria(Criteria.where("month").is(month).and("employeeAttendance.$.*." + day).is(1));

        List<LeaveTracker> attendances = mongoTemplate.find(query, LeaveTracker.class);
        logger.info("Attendance data: " + attendances.toString());
        List<String> employeesOnLeave = new ArrayList<>();
        for (LeaveTracker attendance : attendances) {
            for (Map.Entry<String, Map<String, Integer>> entry : attendance.getEmployeeAttendance().entrySet()) {
                if (entry.getValue().get(day) == 1) {
                    employeesOnLeave.add(entry.getKey());
                }
            }
        }
        return employeesOnLeave;
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
                    attendanceMap.put(String.valueOf(i - 1), (int) cell.getNumericCellValue());
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
}
