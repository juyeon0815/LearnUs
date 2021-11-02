package com.ssafy.backend.controller;

import com.ssafy.backend.dto.Attendance;
import com.ssafy.backend.dto.BroadcastInfo;
import com.ssafy.backend.service.BroadcastService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/broadcast")
@CrossOrigin("*")
public class BroadcastController {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private BroadcastService broadcastService;

    @PostMapping
    @ApiOperation(value = "방송 생성")
    public ResponseEntity<String> insert(@RequestBody BroadcastInfo broadcastInfo) {
        broadcastService.insert(broadcastInfo);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @PatchMapping
    @ApiOperation(value = "방송 수정")
    public ResponseEntity<String> update(@RequestBody BroadcastInfo broadcastInfo) {
        broadcastService.update(broadcastInfo);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @DeleteMapping("/{broadcastId}")
    @ApiOperation(value = "방송 삭제")
    public ResponseEntity<String> delete(@PathVariable("broadcastId") int broadcastId) {
        broadcastService.delete(broadcastId);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @GetMapping("/all")
    @ApiOperation(value = "실시간 방송 조회")
    public ResponseEntity<List<BroadcastInfo>> getBroadcastAll() {
        return new ResponseEntity<>(broadcastService.getBroadcastAll(), HttpStatus.OK);
    }

    @GetMapping("/attendance/{broadcastId}")
    @ApiOperation(value = "참석 명단 가져오기")
    public ResponseEntity<Map<String, List<Attendance>>> getAttendance(@PathVariable("broadcastId") int broadcastId) {
        return new ResponseEntity<>(broadcastService.getAttendance(broadcastId), HttpStatus.OK);
    }

    @PatchMapping("/{broadcastId}/{userId}")
    @ApiOperation(value = "출석 체크")
    public ResponseEntity<String> attend(@PathVariable("broadcastId") int broadcastId, @PathVariable("userId") int userId) {
        broadcastService.attend(broadcastId, userId);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @PostMapping("/start")
    @ApiOperation(value = "방송 시작 -> 관련 MM에 메시지 전송")
    public ResponseEntity<String> broadcastStart(@RequestParam("broadcastId") int broadcastId) {
        broadcastService.start(broadcastId);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @PostMapping("/end/attendance")
    @ApiOperation(value = "방송 종료 -> 미참석 명단 전송")
    public ResponseEntity<String> endAttendance(@RequestParam("broadcastId") int broadcastId) {
        broadcastService.endAttendance(broadcastId);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @PostMapping("/end/attendance/download")
    @ApiOperation(value = "방송 종료 -> 미참석 명단 엑셀 파일 다운로드")
    public ResponseEntity<String> endAttendanceDownload(@RequestParam("broadcastId") int broadcastId, HttpServletResponse response) throws IOException {
        broadcastService.endAttendanceDownload(broadcastId, response);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @PostMapping("/end/gifticon")
    @ApiOperation(value = "방송 종료 -> 기프티콘 명단 전송")
    public ResponseEntity<String> endGifticon(@RequestParam("broadcastId") int broadcastId) {
        broadcastService.endGifticon(broadcastId);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @PostMapping("/end/gifticon/download")
    @ApiOperation(value = "방송 종료 -> 기프티콘 명단 엑셀 파일 다운로드")
    public ResponseEntity<String> endGifticonDownload(@RequestParam("broadcastId") int broadcastId, HttpServletResponse response) throws IOException {
        broadcastService.endGifticonDownload(broadcastId, response);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }
}
