package com.ssafy.helpus.volunteer.controller;

import com.ssafy.helpus.volunteer.dto.TalentDonationReqDto;
import com.ssafy.helpus.volunteer.dto.TalentDonationUpdateReqDto;
import com.ssafy.helpus.volunteer.service.FileService;
import com.ssafy.helpus.volunteer.service.TalentDonationService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/talentDonation")
@CrossOrigin(origins = {"*"}, maxAge = 6000)
public class TalentDonationController {

    private final TalentDonationService talentDonationService;
    private final FileService fileService;


    @ApiOperation(value = "재능기부 글 등록")
    @PostMapping
    public ResponseEntity registerTalentDonation(@RequestPart TalentDonationReqDto talentDonationReqDto, @RequestPart(required = false) MultipartFile[] files,
                                                 @RequestHeader HttpHeaders headers){
        log.info("TalentDonationController registerTalentDonation call");

        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.CREATED;
        try {
                Long memberId = Long.valueOf(headers.get("memberId").get(0));
                String role = headers.get("role").get(0);
                resultMap = talentDonationService.registerTalentDonation(talentDonationReqDto, memberId, files, role);
        } catch (Exception e){
            log.error(e.getMessage());
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity(resultMap, status);
    }

    @ApiOperation(value = "재능기부 글 수정")
    @PutMapping
    public ResponseEntity updateTalentDonation(@RequestPart TalentDonationUpdateReqDto talentDonationUpdateReqDto, @RequestPart(required = false) MultipartFile[] files,
                                               @RequestHeader HttpHeaders headers){
        log.info("TalentDonationController updateTalentDonation call");

        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.CREATED;
        try {
                Long memberId = Long.valueOf(headers.get("memberId").get(0));
                String role = headers.get("role").get(0);
                resultMap = talentDonationService.updateTalentDonation(talentDonationUpdateReqDto, memberId, files, role);

        } catch (Exception e){
            log.error(e.getMessage());
            resultMap.put("message", "봉사글 수정 실패");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity(resultMap, status);
    }

    @ApiOperation(value = "재능기부 글 조회")
    @GetMapping("{volunteerId}")
    public ResponseEntity getTalentDonation(@PathVariable Long volunteerId){
        log.info("TalentDonationController getVolunteer call");

        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            resultMap = talentDonationService.getTalentDonation(volunteerId);
        } catch (Exception e){
            log.error(e.getMessage());
            resultMap.put("message", "error");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity(resultMap, status);
    }
    @ApiOperation(value = "봉사 글 삭제")
    @DeleteMapping("{volunteerId}")
    public ResponseEntity deleteTalentDonation(@PathVariable Long  volunteerId){
        log.info("TalentDonationController deleteTalentDonation call");

        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            resultMap = talentDonationService.deleteTalentDonation(volunteerId);
        }catch (Exception e){
            log.error(e.getMessage());

            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity(resultMap, status);
    }

}
