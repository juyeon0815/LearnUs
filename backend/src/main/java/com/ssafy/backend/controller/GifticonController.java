package com.ssafy.backend.controller;

import com.ssafy.backend.dto.Gifticon;
import com.ssafy.backend.dto.info.GifticonInfo;
import com.ssafy.backend.service.GifticonService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gifticon")
@CrossOrigin("*")
public class GifticonController {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private GifticonService gifticonService;


    @PostMapping
    @ApiOperation(value = "기프티콘 추가")
    public ResponseEntity<String> insert(@RequestBody GifticonInfo gifticonInfo) {
        if (!gifticonService.insert(gifticonInfo)) return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @PatchMapping
    @ApiOperation(value = "기프티콘 수정")
    public ResponseEntity<String> update(@RequestBody GifticonInfo gifticonInfo) {
        if (!gifticonService.update(gifticonInfo)) return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @DeleteMapping("/{gifticonId}")
    @ApiOperation(value = "기프티콘 삭제")
    public ResponseEntity<String> delete(@PathVariable("gifticonId") int gifticonId) {
        if (!gifticonService.delete(gifticonId)) return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @GetMapping("/all/{broadcastId}")
    @ApiOperation(value = "기프티콘 조회")
    public ResponseEntity<List<Gifticon>> getGifticonAll(@PathVariable("broadcastId") int broadcastId) {
        List<Gifticon> gifticonList = gifticonService.getGifticonAll(broadcastId);
        if (gifticonList == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(gifticonList, HttpStatus.OK);
    }
}
