package com.ssafy.backend.controller;

import com.ssafy.backend.dto.info.Password;
import com.ssafy.backend.dto.User;
import com.ssafy.backend.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @ApiOperation(value = "로그인")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Password login, HttpServletResponse res) {

        ResponseEntity<Map<String, Object>> entity = null;

        Map<String, Object> map = userService.login(login.getEmail(), login.getPassword(), res);
        if (map == null) entity = ResponseEntity.badRequest().body(null);
        else if (map.containsKey("msg")) entity = ResponseEntity.badRequest().body(map);
        else if (map.containsKey("fail")) entity = ResponseEntity.status(403).body(map);
        else entity = ResponseEntity.ok().body(map);

        return entity;
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiOperation(value = "교육생 추가")
    public ResponseEntity<String> insert(@RequestParam("excelFile") MultipartFile excelFile) throws IOException{
        if (!userService.insert(excelFile)) return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @PatchMapping(value = "/list", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiOperation(value = "교육생 일괄 수정")
    public ResponseEntity<String> updateList(@RequestParam("excelFile") MultipartFile excelFile) throws IOException {
        if (!userService.updateList(excelFile)) return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @PatchMapping
    @ApiOperation(value = "회원(교육생, 관리자) 수정")
    public ResponseEntity<String> updateUser(@RequestBody User updateUser) {
        if (!userService.updateUser(updateUser)) return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @PatchMapping(value = "/pw")
    @ApiOperation(value = "비밀번호 수정")
    public ResponseEntity<String> updatePW(@RequestBody Password updatePW) {
        if (!userService.updatePW(updatePW.getUserId(), updatePW.getOriginPW(), updatePW.getNewPW())) return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation(value = "교육생 다중 조회. trackList로 넘겨주면 해당 트랙을 듣는 학생들 넘겨줌")
    public ResponseEntity<Map<String, List<User>>> getTrackUser(@RequestParam("trackList") List<String> trackList) {
        Map<String, List<User>> map = userService.getTrackUser(trackList);
        if (map == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    @ApiOperation(value = "모든 교육생 정보 넘겨줌")
    public ResponseEntity<Map<Integer, List<User>>> getUserAll() {
        Map<Integer, List<User>> map = userService.getUserAll();
        if (map == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping(value = "/{type}/{search}")
    @ApiOperation(value = "교육생 1명 조회 type : id (학번), type : name (이름)")
    public ResponseEntity<User> getUser(@PathVariable("type") String type, @PathVariable("search") String search) {
        User user = userService.getUser(type, search);
        if (user!=null) {
            user.setPassword("");
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/searchId/{userId}/{phone}")
    @ApiOperation(value = "아이디 찾기. 학번과 전화번호를 입력하면 이메일 넘겨줌")
    public ResponseEntity<String> getEmail(@PathVariable("userId") int userId, @PathVariable("phone") String phone) {
        String email = userService.getEmail(userId, phone);
        if (email == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(email, HttpStatus.OK);
    }

    @GetMapping(value = "/searchPW/{email}/{userId}/{phone}")
    @ApiOperation(value = "비밀번호 찾기. 이메일, 학번, 전화번호를 입력하면 비밀번호 초기화 가능")
    public ResponseEntity<String> getPwCheck(@PathVariable("email") String email, @PathVariable("userId") int userId, @PathVariable("phone") String phone) {
        if (!userService.getPwCheck(email, userId, phone)) return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @PatchMapping(value = "/resetPW")
    @ApiOperation(value = "비밀번호 초기화")
    public ResponseEntity<String> resetPW(@RequestBody Password resetPW) {
        if (!userService.resetPW(resetPW.getUserId(), resetPW.getNewPW())) return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @GetMapping("/region")
    @ApiOperation(value = "지역 리스트")
    public ResponseEntity<List<String>> getRegionList() {
        List<String> regionList = userService.getRegionList();
        if (regionList == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(regionList, HttpStatus.OK);
    }

}
