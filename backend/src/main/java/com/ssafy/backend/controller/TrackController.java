package com.ssafy.backend.controller;

import com.ssafy.backend.dto.Track;
import com.ssafy.backend.service.TrackService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/track")
@CrossOrigin("*")
public class TrackController {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private TrackService trackService;

    @PostMapping
    @ApiOperation(value = "Track 추가. semester : 학기, name : 트랙 이름, subject : 트랙 주제(2학기만)")
    public ResponseEntity<String> insert(@RequestParam("semester") int semester, @RequestParam("name") String name, @RequestParam(value = "subject", required = false, defaultValue = "") String subject) {
        trackService.insert(semester, name, subject);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @PatchMapping
    @ApiOperation(value = "Track 수정")
    public ResponseEntity<String> update(@RequestBody Track track) {
        trackService.update(track);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @DeleteMapping("/{trackId}")
    @ApiOperation(value = "Track 삭제")
    public ResponseEntity<String> delete(@PathVariable("trackId") int trackId) {
        trackService.delete(trackId);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @GetMapping("/all")
    @ApiOperation(value = "Track 전체 조회")
    public ResponseEntity<List<Track>> getTrackAll() {
        return new ResponseEntity<>(trackService.getTrackAll(), HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation(value = "현재 트랙 주제 조회. 현재 트랙 주제가 공통이라면 공통에 관련된 트랙들만 보여줌")
    public ResponseEntity<List<Track>> getTrackCurrent() {
        return new ResponseEntity<>(trackService.getTrackCurrent(), HttpStatus.OK);
    }
}

