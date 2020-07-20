package com.sejong.eatnow.web;

import com.sejong.eatnow.service.UserService;
import com.sejong.eatnow.web.dto.UserRequestDto;
import com.sejong.eatnow.web.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.json.simple.JSONObject;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log
@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("/user/*")
public class UserController {

    private final UserService service;

    @PostMapping("/test")
    public ResponseEntity<JSONObject> json_test() {

        JSONObject json = new JSONObject();
        ResponseEntity<JSONObject> entity = null;

        json.put("response", "test..json type");
        entity = new ResponseEntity<>(json, HttpStatus.OK);
            /*
            - 반환시 ResponseEntity<String>을 jsonObject로 바꾼게 차이가 있나? -> (((어쨋든 ResponseEntity껍데기는 필요)))
            - 차이 없으면 JSONObject 안 만들고 그냥 string으로 "{/"response/", /"test/"}"하면 되는거 아닌가?
             */

        log.info(entity.toString());
        return entity;
    }

    @PostMapping("/insert")
    public ResponseEntity<String> insert(@RequestBody UserRequestDto dto) {
        log.info("insert (controller) 진입: " + dto.getEmail() + ", " + dto.getName());
        JSONObject json = new JSONObject();
        ResponseEntity<String> entity = null;
        try {
            service.insert(dto);
            json.put("response", "success");
            log.info(json.toJSONString());
            entity = new ResponseEntity<>(json.toJSONString(), HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            json.put("response", e.getMessage());
            log.info(json.toJSONString());
            entity = new ResponseEntity<>(json.toJSONString(), HttpStatus.BAD_REQUEST);
        }
        log.info(entity.getBody());
        return entity;
    }

    @PostMapping("/check/admin")
    public ResponseEntity<String> checkAdmin(@RequestBody UserRequestDto dto) {
        log.info("check admin 진입");
        String res = null;
        JSONObject json = new JSONObject();
        ResponseEntity<String> entity = null;
        try {
            res = service.isAdmin(dto);
            if (res.equals("yes")) {
                json.put("response", "yes");
                entity = new ResponseEntity<>(json.toJSONString(), HttpStatus.OK);
            } else {
                json.put("response", "no");
                entity = new ResponseEntity<>(json.toJSONString(), HttpStatus.OK);
            }
        } catch (NullPointerException e) {
            json.put("response", e.getMessage());
            entity = new ResponseEntity<>(json.toJSONString(), HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody UserRequestDto dto) {
        log.info("update (controller) 진입");
        JSONObject json = new JSONObject();
        ResponseEntity<String> entity = null;
        try {
            service.update(id, dto);
            json.put("response", "success");
            entity = new ResponseEntity<>(json.toJSONString(), HttpStatus.OK);
        } catch (NullPointerException e) {
            json.put("response", e.getMessage());
            entity = new ResponseEntity<>(json.toJSONString(), HttpStatus.BAD_REQUEST);
        } catch (DataAccessException e) {
            json.put("response", e.getMessage());
            entity = new ResponseEntity<>(json.toJSONString(), HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<UserResponseDto> find(@PathVariable Long id) {
        log.info("find (controller) 진입");
        ResponseEntity<UserResponseDto> entity = null;

        try {
            UserResponseDto responseDto = service.findById(id);
            entity = new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (DataAccessException e) {
            log.warning("find user failed...." + e.getMessage());
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NullPointerException e) {
            log.warning("find user failed...." + e.getMessage());
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<UserResponseDto>> findAllDesc() {
        log.info("findAll 진입.....");
        return new ResponseEntity<>(service.findAllDesc(), HttpStatus.OK);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        log.info("delete (controller) 진입");
        JSONObject json = new JSONObject();
        ResponseEntity<String> entity = null;
        try {
            service.deleteById(id);
            json.put("response", "success");
            entity = new ResponseEntity<>(json.toJSONString(), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            json.put("response", e.getMessage());
            entity = new ResponseEntity<>(json.toJSONString(), HttpStatus.BAD_REQUEST);
        }
        return entity;
    }
}
