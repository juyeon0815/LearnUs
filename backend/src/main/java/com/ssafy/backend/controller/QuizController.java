package com.ssafy.backend.controller;

import com.ssafy.backend.dto.info.QuizInfo;
import com.ssafy.backend.dto.info.QuizRankInfo;
import com.ssafy.backend.service.quiz.QuizService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quiz")
@CrossOrigin("*")
public class QuizController {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private QuizService quizService;

    @PostMapping
    @ApiOperation(value = "퀴즈 추가")
    public ResponseEntity<String> insert(@RequestBody QuizInfo quizInfo) {
        if (!quizService.insert(quizInfo)) return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @PatchMapping
    @ApiOperation(value = "퀴즈 수정")
    public ResponseEntity<String> update(@RequestBody QuizInfo quizInfo) {
        if (!quizService.update(quizInfo)) return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @DeleteMapping("/{quizId}")
    @ApiOperation(value = "퀴즈 삭제")
    public ResponseEntity<String> delete(@PathVariable("quizId") int quizId) {
        if (!quizService.delete(quizId)) return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @GetMapping("/all/{broadcastId}")
    @ApiOperation(value = "퀴즈 조회")
    public ResponseEntity<List<QuizInfo>> getQuizInfoAll(@PathVariable("broadcastId") int broadcastId) {
        List<QuizInfo> quizInfoList = quizService.getQuizInfoAll(broadcastId);
        if (quizInfoList == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(quizInfoList, HttpStatus.OK);
    }

    @GetMapping("/all/after/{broadcastId}")
    @ApiOperation(value = "방송 후 퀴즈 조회 (순위, 참여 정보 등)")
    public ResponseEntity<List<QuizRankInfo>> getQuizInfoAllAfterBroadcast(@PathVariable("broadcastId") int broadcastId) {
        List<QuizRankInfo> quizRankInfoList = quizService.getQuizInfoAllAfterBroadcast(broadcastId);
        if (quizRankInfoList == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(quizRankInfoList, HttpStatus.OK);
    }
}
