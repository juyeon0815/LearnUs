package com.ssafy.backend.controller;

import com.ssafy.backend.dto.info.BroadcastReplayInfo;
import com.ssafy.backend.service.broadcast.BroadcastReplayService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/broadcastReplay")
@CrossOrigin("*")
public class BroadcastReplayController {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private BroadcastReplayService broadcastReplayService;

    @PostMapping
    @ApiOperation(value = "방송 다시보기 추가")
    public ResponseEntity<String> insert(@RequestBody BroadcastReplayInfo broadcastReplayInfo) {
        if (!broadcastReplayService.insert(broadcastReplayInfo)) return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @PatchMapping
    @ApiOperation(value = "방송 다시보기 수정")
    public ResponseEntity<String> update(@RequestBody BroadcastReplayInfo broadcastReplayInfo) {
        if (!broadcastReplayService.update(broadcastReplayInfo)) return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @DeleteMapping("/{broadcastReplayId}")
    @ApiOperation(value = "방송 다시보기 삭제")
    public ResponseEntity<String> delete(@PathVariable("broadcastReplayId") int broadcastReplayId) {
        if (!broadcastReplayService.delete(broadcastReplayId)) return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @GetMapping("/all")
    @ApiOperation(value = "방송 다시보기 전체 조회")
    public ResponseEntity<List<BroadcastReplayInfo>> getBroadcastReplayAll() {
        List<BroadcastReplayInfo> broadcastReplayInfoList = broadcastReplayService.getBroadcastReplayAll();
        if (broadcastReplayInfoList == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(broadcastReplayInfoList, HttpStatus.OK);
    }

    @GetMapping("/all/{ordinalNo}")
    @ApiOperation(value = "방송 다시보기 기수별 전체 조회")
    public ResponseEntity<List<BroadcastReplayInfo>> getBroadcastReplayAllOrdinalNo(@PathVariable("ordinalNo") int ordinalNo) {
        List<BroadcastReplayInfo> broadcastReplayInfoList = broadcastReplayService.getBroadcastReplayAllOrdinalNo(ordinalNo);
        if (broadcastReplayInfoList == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(broadcastReplayInfoList, HttpStatus.OK);
    }

    @GetMapping("/track/{trackId}/{ordinalNo}")
    @ApiOperation(value = "트랙별 방송 다시보기 조회")
    public ResponseEntity<List<BroadcastReplayInfo>> getBroadcastReplayTrack(@PathVariable("trackId") int trackId, @PathVariable("ordinalNo") int ordinalNo) {
        List<BroadcastReplayInfo> broadcastReplayInfoList = broadcastReplayService.getBroadcastReplayTrack(trackId, ordinalNo);
        if (broadcastReplayInfoList == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(broadcastReplayInfoList, HttpStatus.OK);
    }

    @GetMapping("/{broadcastReplayId}")
    @ApiOperation(value = "방송 다시보기 1개 조회")
    public ResponseEntity<BroadcastReplayInfo> getBroadcastReplay(@PathVariable("broadcastReplayId") int broadcastReplayId) {
        BroadcastReplayInfo broadcastReplayInfo = broadcastReplayService.getBroadcastReplay(broadcastReplayId);
        if (broadcastReplayInfo == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(broadcastReplayInfo, HttpStatus.OK);
    }

    @GetMapping("/getId/{broadcastId}")
    @ApiOperation(value = "방송 아이디 넘겨주면 다시보기 아이디 조회")
    public ResponseEntity<Integer> getBroadcastReplayId(@PathVariable("broadcastId") int broadcastId) {
        int broadcastReplayId = broadcastReplayService.getBroadcastReplayId(broadcastId);
        if (broadcastReplayId == 0) return new ResponseEntity<>(0, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(broadcastReplayId, HttpStatus.OK);
    }

    @GetMapping("/index/{userId}")
    @ApiOperation(value = "기수 관련 방송 다시보기 3개 불러오기")
    public ResponseEntity<List<BroadcastReplayInfo>> getBroadcastReplayLimit3(@PathVariable("userId") int userId) {
        List<BroadcastReplayInfo> broadcastReplayInfoList = broadcastReplayService.getBroadcastReplayLimit3(userId);
        if (broadcastReplayInfoList == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(broadcastReplayInfoList, HttpStatus.OK);
    }
}
