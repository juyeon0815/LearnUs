package com.ssafy.backend.controller;

import com.ssafy.backend.dto.MattermostInfo;
import com.ssafy.backend.service.MattermostService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mattermost")
@CrossOrigin("*")
public class MattermostController {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private MattermostService mattermostService;

    @GetMapping("/select")
    @ApiOperation(value = "프로 채널 시 기수 선택")
    public ResponseEntity<List<Integer>> selectOrdinalNo() {
        return new ResponseEntity<>(mattermostService.selectOrdinalNo(), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "MM 채널 추가")
    public ResponseEntity<String> insert(@RequestBody MattermostInfo mattermostInfo) {
        mattermostService.insert(mattermostInfo);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @PatchMapping
    @ApiOperation(value = "MM 채널 수정")
    public ResponseEntity<String> update(@RequestBody MattermostInfo mattermostInfo) {
        mattermostService.update(mattermostInfo);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @DeleteMapping("/{mattermostId}")
    @ApiOperation(value = "MM 채널 삭제")
    public ResponseEntity<String> delete(@PathVariable("mattermostId") int mattermostId) {
        if (mattermostService.delete(mattermostId)) return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        return new ResponseEntity<>(FAIL, HttpStatus.BAD_GATEWAY);
    }

    @GetMapping("/all")
    @ApiOperation(value = "MM 채널 조회")
    public ResponseEntity<List<MattermostInfo>> getMattermostAll() {
        return new ResponseEntity<>(mattermostService.getMattermostAll(), HttpStatus.OK);
    }
}
