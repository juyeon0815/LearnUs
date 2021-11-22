package com.ssafy.backend.controller;

import com.ssafy.backend.dto.info.MattermostInfo;
import com.ssafy.backend.service.mattermost.MattermostService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mattermost")
@CrossOrigin("*")
public class MattermostController {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private MattermostService mattermostService;

    @GetMapping("/select")
    @ApiOperation(value = "프로 채널 시 기수 선택")
    public ResponseEntity<List<Integer>> selectOrdinalNo() {
        List<Integer> ordinalList = mattermostService.selectOrdinalNo();
        if (ordinalList == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(ordinalList, HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "MM 채널 추가")
    public ResponseEntity<String> insert(@RequestBody MattermostInfo mattermostInfo) {
        if (!mattermostService.insert(mattermostInfo)) return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @PatchMapping
    @ApiOperation(value = "MM 채널 수정")
    public ResponseEntity<String> update(@RequestBody MattermostInfo mattermostInfo) {
        if (!mattermostService.update(mattermostInfo)) return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @DeleteMapping("/{mattermostId}")
    @ApiOperation(value = "MM 채널 삭제")
    public ResponseEntity<String> delete(@PathVariable("mattermostId") int mattermostId) {
        if (!mattermostService.delete(mattermostId)) return new ResponseEntity<>(FAIL, HttpStatus.BAD_GATEWAY);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @GetMapping("/all")
    @ApiOperation(value = "MM 채널 조회")
    public ResponseEntity<List<MattermostInfo>> getMattermostAll() {
        List<MattermostInfo> mattermostInfoList = mattermostService.getMattermostAll();
        if (mattermostInfoList == null) return new ResponseEntity<>(null, HttpStatus.BAD_GATEWAY);
        return new ResponseEntity<>(mattermostInfoList, HttpStatus.OK);
    }
}
