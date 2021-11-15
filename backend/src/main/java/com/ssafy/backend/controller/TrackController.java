package com.ssafy.backend.controller;

import com.ssafy.backend.dto.Track;
import com.ssafy.backend.dto.info.TrackInfo;
import com.ssafy.backend.service.track.TrackService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api//track")
@CrossOrigin("*")
public class TrackController {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private TrackService trackService;

    @PostMapping
    @ApiOperation(value = "Track 추가")
    public ResponseEntity<String> insert(@RequestBody TrackInfo trackInfo) {
        if (!trackService.insert(trackInfo)) return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @PatchMapping
    @ApiOperation(value = "Track 수정")
    public ResponseEntity<String> update(@RequestBody TrackInfo trackInfo) {
        if (!trackService.update(trackInfo)) return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @DeleteMapping("/{trackId}")
    @ApiOperation(value = "Track 삭제")
    public ResponseEntity<String> delete(@PathVariable("trackId") int trackId) {
        if (!trackService.delete(trackId)) return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @GetMapping("/all")
    @ApiOperation(value = "Track 전체 조회")
    public ResponseEntity<List<Track>> getTrackAll() {
        List<Track> trackList = trackService.getTrackAll();
        if (trackList == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(trackList, HttpStatus.OK);
    }

    @GetMapping("/{subjectId}")
    @ApiOperation(value = "TrackSubject별 Track 조회")
    public ResponseEntity<List<Track>> getTrackSubject(@PathVariable("subjectId") int subjectId) {
        List<Track> trackList = trackService.getTrackSubject(subjectId);
        if (trackList == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(trackList, HttpStatus.OK);
    }

    @GetMapping("/current")
    @ApiOperation(value = "현재 TrackSubject 관련 Track 조회")
    public ResponseEntity<List<Track>> getCurrentTrackSubject() {
        List<Track> trackList = trackService.getCurrentTrackSubject();
        if (trackList == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(trackList, HttpStatus.OK);
    }
}

