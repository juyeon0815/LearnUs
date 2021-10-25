package com.ssafy.backend.controller;

import com.ssafy.backend.dto.Schedule;
import com.ssafy.backend.dto.Track;
import com.ssafy.backend.service.ScheduleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/schedule")
@CrossOrigin("*")
public class ScheduleController {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping
    @ApiOperation(value = "Schedule 추가")
    public ResponseEntity<String> insert(@RequestBody Schedule schedule) {
        scheduleService.insert(schedule);
        return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }

    @PatchMapping
    @ApiOperation(value = "Schedule 수정")
    public ResponseEntity<String> update(@RequestBody Schedule schedule) {
        scheduleService.update(schedule);
        return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }

    @DeleteMapping("/{scheduleId}")
    @ApiOperation(value = "Schedule 삭제")
    public ResponseEntity<String> delete(@PathVariable("scheduleId") int scheduleId) {
        scheduleService.delete(scheduleId);
        return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }

    @GetMapping("/all")
    @ApiOperation(value = "Schedule 전체 조회")
    public ResponseEntity<List<Schedule>> getScheduleAll() {
        return new ResponseEntity<List<Schedule>>(scheduleService.getScheduleAll(), HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation(value = "오늘 Schedule 조회")
    public ResponseEntity<Schedule> getScheduleCurrent() {
        Schedule schedule = scheduleService.getScheduleCurrent();
        if (schedule != null) return new ResponseEntity<Schedule>(schedule, HttpStatus.OK);
        return new ResponseEntity<Schedule>(schedule, HttpStatus.NO_CONTENT);

    }
}
