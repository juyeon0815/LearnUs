package com.ssafy.backend.service;

import com.ssafy.backend.dto.Attendance;
import com.ssafy.backend.dto.Broadcast;
import com.ssafy.backend.dto.Gifticon;
import com.ssafy.backend.dto.User;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ExcelServiceImpl implements ExcelService{
    @Override
    public Sheet excelCheck(MultipartFile excelFile) throws IOException {
        String extension = FilenameUtils.getExtension(excelFile.getOriginalFilename());

        if (!extension.equals("xlsx") && !extension.equals("xls")) throw new IOException("엑셀 파일만 업로드 가능");

        Workbook workbook = null;

        if (extension.equals("xlsx")) {
            workbook = new XSSFWorkbook(excelFile.getInputStream());
        } else if (extension.equals("xls")) {
            workbook = new HSSFWorkbook(excelFile.getInputStream());
        }

        return workbook.getSheetAt(0);
    }

    @Override
    public boolean createExcelAttendance(Broadcast broadcast, List<Attendance> attendanceList, HttpServletResponse response) throws IOException {
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("미참석자 명단");
            Row row = null;
            Cell cell = null;
            int rowNum = 0;

            // Header
            row = sheet.createRow(rowNum++);
            cell = row.createCell(0);
            cell.setCellValue("번호");
            cell = row.createCell(1);
            cell.setCellValue("기수");
            cell = row.createCell(2);
            cell.setCellValue("학번");
            cell = row.createCell(3);
            cell.setCellValue("이름");
            cell = row.createCell(4);
            cell.setCellValue("지역");
            cell = row.createCell(5);
            cell.setCellValue("반");

            // Body
            for (int i = 0; i < attendanceList.size(); i++) {
                User user = attendanceList.get(i).getUser();
                row = sheet.createRow(rowNum++);
                cell = row.createCell(0);
                cell.setCellValue(i + 1);
                cell = row.createCell(1);
                cell.setCellValue(user.getOrdinalNo());
                cell = row.createCell(2);
                cell.setCellValue(user.getUserId());
                cell = row.createCell(3);
                cell.setCellValue(user.getName());
                cell = row.createCell(4);
                cell.setCellValue(user.getRegion());
                cell = row.createCell(5);
                cell.setCellValue(user.getClassNo());
            }

            // 타입과 파일명 지정
            String formatDate = broadcast.getBroadcastDate().format(DateTimeFormatter.ofPattern("yy.MM.dd"));
            response.setContentType("ms-vnd/excel");
            response.setHeader("content-disposition", "attachment;filename=[" + broadcast.getTitle() + "] attendance (" + formatDate + ").xlsx");

            // file output
            workbook.write(response.getOutputStream());
            response.getOutputStream().close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean createExcelGifticon(Broadcast broadcast, List<Gifticon> gifticonList, HttpServletResponse response) throws IOException {
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("기프티콘 당첨자 명단");
            Row row = null;
            Cell cell = null;
            int rowNum = 0;

            // Header
            row = sheet.createRow(rowNum++);
            cell = row.createCell(0);
            cell.setCellValue("번호");
            cell = row.createCell(1);
            cell.setCellValue("학번");
            cell = row.createCell(2);
            ;
            cell.setCellValue("이름");
            cell = row.createCell(3);
            cell.setCellValue("번호");

            // Body
            for (int i = 0; i < gifticonList.size(); i++) {
                User user = gifticonList.get(i).getUser();
                row = sheet.createRow(rowNum++);
                cell = row.createCell(0);
                cell.setCellValue(i + 1);
                cell = row.createCell(1);
                cell.setCellValue(user.getUserId());
                cell = row.createCell(2);
                cell.setCellValue(user.getName());
                cell = row.createCell(3);
                cell.setCellValue(user.getPhone());
            }

            // 타입과 파일명 지정
            String formatDate = broadcast.getBroadcastDate().format(DateTimeFormatter.ofPattern("yy.MM.dd"));
            response.setContentType("ms-vnd/excel");
            response.setHeader("content-disposition", "attachment;filename=[" + broadcast.getTitle() + "] gifticon (" + formatDate + ").xlsx");

            // file output
            workbook.write(response.getOutputStream());
            response.getOutputStream().close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
