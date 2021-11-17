package com.ssafy.backend.controller;

import com.ssafy.backend.dto.TrackSubject;
import com.ssafy.backend.dto.info.TrackSubjectInfo;
import com.ssafy.backend.service.track.TrackSubjectService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trackSubject")
@CrossOrigin("*")
public class TrackSubjectController {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private TrackSubjectService trackSubjectService;

    @GetMapping("/ordinalNo")
    @ApiOperation(value = "현재 기수 조회")
    public ResponseEntity<List<Integer>> getOrdinalNo() {
        List<Integer> ordinalList = trackSubjectService.getOrdinalNo();
        if (ordinalList == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(ordinalList, HttpStatus.OK);
    }

    @GetMapping("/semester/{ordinalNo}")
    @ApiOperation(value = "현재 기수 넘겨주면 학기 조회")
    public ResponseEntity<Integer> getSemester(@PathVariable("ordinalNo") int ordinalNo) {
        int semester = trackSubjectService.getSemester(ordinalNo);
        if (semester == 0) return new ResponseEntity<>(0, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(semester, HttpStatus.OK);
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
        List<TrackSubject> trackSubjectList = trackSubjectService.getTrackSubjectAll();
        if (trackSubjectList == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(trackSubjectList, HttpStatus.OK);
    }

    @GetMapping("/current")
    @ApiOperation(value = "현재 트랙 주제 조회")
    public ResponseEntity<TrackSubject> getCurrentTrackSubject() {
        TrackSubject trackSubject = trackSubjectService.getCurrentTrackSubject();
        if (trackSubject == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(trackSubject, HttpStatus.OK);
    }

    @PatchMapping("/current/{newSubjectId}")
    @ApiOperation(value = "현재 트랙 주제 수정")
    public ResponseEntity<String> currentTrackSubjectUpdate(@PathVariable("newSubjectId") int newSubjectId) {
        if (!trackSubjectService.currentTrackSubjectUpdate(newSubjectId)) return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @GetMapping("/{semester}")
    @ApiOperation(value = "학기별 트랙 주제 조회")
    public ResponseEntity<List<TrackSubject>> getTrackSubjectSemester(@PathVariable("semester") int semester) {
        List<TrackSubject> trackSubjectList = trackSubjectService.getTrackSubjectSemester(semester);
        if (trackSubjectList == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(trackSubjectList, HttpStatus.OK);
    }
}
