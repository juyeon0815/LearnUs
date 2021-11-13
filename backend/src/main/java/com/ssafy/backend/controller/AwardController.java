package com.ssafy.backend.controller;

import com.ssafy.backend.dto.Award;
import com.ssafy.backend.service.AwardService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/award")
@CrossOrigin("*")
public class AwardController {
    @Autowired
    private AwardService awardService;

    @GetMapping
    @ApiOperation(value = "오늘의 award 조회")
    public ResponseEntity<List<Award>> getAward() {
        List<Award> awardList = awardService.getAward();
        if (awardList == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(awardList, HttpStatus.OK);
    }
}
