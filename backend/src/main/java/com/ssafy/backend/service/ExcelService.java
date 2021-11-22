package com.ssafy.backend.service;

import com.ssafy.backend.dto.Attendance;
import com.ssafy.backend.dto.Broadcast;
import com.ssafy.backend.dto.Gifticon;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface ExcelService {
    Sheet excelCheck(MultipartFile excelFile) throws IOException;
    boolean createExcelAttendance(Broadcast broadcast, List<Attendance> attendanceList, HttpServletResponse response) throws IOException;
    boolean createExcelGifticon(Broadcast broadcast, List<Gifticon> gifticonList, HttpServletResponse response) throws IOException;
}
