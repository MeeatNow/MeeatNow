package com.sejong.eatnow.web;

import com.sejong.eatnow.service.LobyService;
import com.sejong.eatnow.web.dto.LobyRequestDto;
import com.sejong.eatnow.web.dto.LobyResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log
@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/loby/*")
public class LobyController {
    private final LobyService service;

    @PostMapping("/insert")
    public ResponseEntity<JSONObject> insert(@RequestBody LobyRequestDto dto) {
        JSONObject json = new JSONObject();
        ResponseEntity<JSONObject> entity = null;
        try {
            service.insert(dto);
            json.put("response","success");
            entity = new ResponseEntity<>(json, HttpStatus.OK);
            log.info("saving Loby successfully....");
        } catch (Exception e) {
            log.warning(e.getMessage());
            json.put("response",e.getMessage());
            entity = new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);

        }
        return entity;
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<JSONObject> update(@PathVariable Long id, @RequestBody LobyRequestDto dto) {
        JSONObject json = new JSONObject();
        ResponseEntity<JSONObject> entity = null;

        try {
            service.update(id, dto);
            json.put("response","success");
            entity = new ResponseEntity<>(json, HttpStatus.OK);
        } catch (NullPointerException e) {
            json.put("response", e.getMessage());
            log.warning("loby update failed...." + e.getMessage());
            entity = new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<LobyResponseDto> find(@PathVariable Long id) {
        ResponseEntity<LobyResponseDto> entity = null;
        try {
            LobyResponseDto dto = service.findById(id);
            log.info("find " + id + ":" + dto.getHostName() + "," + dto.getOpenLink() + "," + dto.getTitle() + "," + dto.getId() + "," + dto.getLatitude() + "," + dto.getLongitude() + "," + dto.getMeetingDate());
            entity = new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (NullPointerException e) {
            log.warning("loby find failed...." + e.getMessage());
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<LobyResponseDto>> findAllByDesc() {
        List<LobyResponseDto> list = service.findAllByDesc();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<JSONObject> delete(@PathVariable Long id) {
        JSONObject json = new JSONObject();
        ResponseEntity<JSONObject> entity = null;
        try {
            service.delete(id);
            json.put("response", "success");
            entity = new ResponseEntity<>(json, HttpStatus.OK);
        } catch (NullPointerException e) {
            json.put("response", e.getMessage());
            log.warning("delete loby failed...." + e.getMessage());
            entity = new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
        }
        return entity;
    }
}
