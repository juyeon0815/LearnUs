package com.ssafy.backend.controller;

import com.ssafy.backend.dto.TrackSubject;
import com.ssafy.backend.dto.TrackSubjectInfo;
import com.ssafy.backend.service.TrackSubjectService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trackSubject")
@CrossOrigin("*")
public class TrackSubjectController {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private TrackSubjectService trackSubjectService;

    @GetMapping("/ordinalNo")
    @ApiOperation(value = "현재 기수 조회")
    public ResponseEntity<List<String>> getOrdinalNo() {
        return new ResponseEntity<>(trackSubjectService.getOrdinalNo(), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "트랙 주제 추가")
    public ResponseEntity<String> insert(@RequestBody TrackSubjectInfo trackSubjectInfo) {
        if (!trackSubjectService.insert(trackSubjectInfo)) return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @PatchMapping
    @ApiOperation(value = "트랙 주제 수정")
    public ResponseEntity<String> update(@RequestBody TrackSubjectInfo trackSubjectInfo) {
        if (!trackSubjectService.update(trackSubjectInfo)) return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @DeleteMapping("/{trackSubjectId}")
    @ApiOperation(value = "트랙 주제 삭제")
    public ResponseEntity<String> delete(@PathVariable("trackSubjectId") int trackSubjectId) {
        if (!trackSubjectService.delete(trackSubjectId)) return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @GetMapping("/all")
    @ApiOperation(value = "트랙 주제 전체 조회")
    public ResponseEntity<List<TrackSubject>> getTrackSubjectAll() {
        return new ResponseEntity<>(trackSubjectService.getTrackSubjectAll(), HttpStatus.OK);
    }

    @GetMapping("/current")
    @ApiOperation(value = "현재 트랙 주제 조회")
    public ResponseEntity<TrackSubject> getCurrentTrackSubject() {
        TrackSubject trackSubject = trackSubjectService.getCurrentTrackSubject();
        if (trackSubject == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(trackSubject, HttpStatus.OK);
    }

    @PatchMapping("/current/{newSubjectName}")
    @ApiOperation(value = "현재 트랙 주제 수정")
    public ResponseEntity<String> currentTrackSubjectUpdate(@PathVariable("newSubjectName") String newSubjectName) {
        if (!trackSubjectService.currentTrackSubjectUpdate(newSubjectName)) return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }
}
