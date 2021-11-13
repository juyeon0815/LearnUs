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
@RequestMapping("/broadcastReplay")
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

    @GetMapping("/all/{ordinalNo}")
    @ApiOperation(value = "방송 다시보기 전체 조회")
    public ResponseEntity<List<BroadcastReplayInfo>> getBroadcastReplayAll(@PathVariable("ordinalNo") int ordinalNo) {
        List<BroadcastReplayInfo> broadcastReplayInfoList = broadcastReplayService.getBroadcastReplayAll(ordinalNo);
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
}
