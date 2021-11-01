package com.ssafy.backend.service;

import com.ssafy.backend.dto.Attendance;
import com.ssafy.backend.dto.Broadcast;
import com.ssafy.backend.dto.User;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface ExcelService {
    public Sheet excelCheck(MultipartFile excelFile) throws IOException;
    public void createExcelAttendance(Broadcast broadcast, List<Attendance> attendanceList, HttpServletResponse response) throws IOException;
}
