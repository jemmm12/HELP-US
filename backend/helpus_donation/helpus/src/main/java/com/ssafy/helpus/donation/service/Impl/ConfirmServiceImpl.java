package com.ssafy.helpus.donation.service.Impl;

import com.ssafy.helpus.donation.dto.Confirm.ConfirmListResDto;
import com.ssafy.helpus.donation.dto.Confirm.ConfirmReqDto;
import com.ssafy.helpus.donation.dto.Confirm.ConfirmResDto;
import com.ssafy.helpus.donation.dto.Confirm.ConfirmUpdateReqDto;
import com.ssafy.helpus.donation.entity.DonationConfirm;
import com.ssafy.helpus.donation.repository.DonationConfirmRepository;
import com.ssafy.helpus.donation.service.ConfirmService;
import com.ssafy.helpus.donation.service.FileService;
import com.ssafy.helpus.member.service.MemberService;
import com.ssafy.helpus.utils.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConfirmServiceImpl implements ConfirmService {

    private final FileService fileService;
    private final MemberService memberService;

    private final DonationConfirmRepository confirmRepository;

    @Override
    public Map<String, Object> registerConfirm(ConfirmReqDto confirmDto, Long memberId, List<MultipartFile> files) throws Exception {
        log.info("ConfirmService registerConfirm call");

        Map<String, Object> resultMap = new HashMap<>();

        if(confirmRepository.existsByDonationId(confirmDto.getDonationId())) {
            resultMap.put("message", Message.CONFIRM_FIND);
            return resultMap;
        }

        //게시글 저장
        DonationConfirm confirm = DonationConfirm.builder()
                .donationId(confirmDto.getDonationId())
                .memberId(memberId)
                .title(confirmDto.getTitle())
                .content(confirmDto.getContent()).build();
        confirmRepository.save(confirm);

        //게시글 파일 저장
        fileService.confirmFileSave(confirm, files);

        resultMap.put("message", Message.CONFIRM_REGISTER_SUCCESS);
        resultMap.put("boardId", confirm.getDonationConfirmId());

        return resultMap;
    }

    @Override
    @Transactional
    public Map<String, Object> updateConfirm(ConfirmUpdateReqDto confirmDto, List<MultipartFile> files) throws Exception {
        log.info("ConfirmService updateConfirm call");

        Map<String, Object> resultMap = new HashMap<>();

        Optional<DonationConfirm> confirm = confirmRepository.findById(confirmDto.getDonationConfirmId());
        if(!confirm.isPresent()) {
            resultMap.put("message", Message.CONFIRM_NOT_FOUND);
            return resultMap;
        }
        confirm.get().setTitle(confirmDto.getTitle());
        confirm.get().setContent(confirmDto.getContent());
        confirm.get().setUpdateDate(LocalDateTime.now());

        //게시글 파일 삭제 후 저장
        fileService.confirmFileDelete(confirm.get().getImages());
        fileService.confirmFileSave(confirm.get(), files);

        resultMap.put("message", Message.CONFIRM_UPDATE_SUCCESS);
        return resultMap;
    }

    @Override
    @Transactional
    public Map<String, Object> getConfirm(Long donationConfirmId) throws Exception {
        log.info("ConfirmService getConfirm call");

        Map<String, Object> resultMap = new HashMap<>();

        Optional<DonationConfirm> confirm = confirmRepository.findById(donationConfirmId);
        if(!confirm.isPresent()) {
            resultMap.put("message", Message.CONFIRM_NOT_FOUND);
            return resultMap;
        }

        ConfirmResDto confirmDto = ConfirmResDto.builder()
                .donationId(confirm.get().getDonationId())
                .memberId(confirm.get().getMemberId())
                .title(confirm.get().getTitle())
                .content(confirm.get().getContent())
                .createDate(confirm.get().getCreateDate())
                .updateDate(confirm.get().getUpdateDate())
                .images(fileService.getConfirmFileList(confirm.get().getImages())).build();

        resultMap.put("message", Message.CONFIRM_FIND_SUCCESS);
        resultMap.put("confirm", confirmDto);
        return resultMap;
    }

    @Override
    public Map<String, Object> confirmList(Long memberId, int page) {
        log.info("ConfirmService getConfirm call");

        Map<String, Object> resultMap = new HashMap<>();

        Page<DonationConfirm> confirms = null;
        if(memberId == null) {
            confirms = confirmRepository.findAll(PageRequest.of(page, 10, Sort.by("donationConfirmId").descending()));
        } else {
            confirms = confirmRepository.findByMemberId(memberId, PageRequest.of(page, 10, Sort.by("donationConfirmId").descending()));
        }

        if(confirms.isEmpty()) {
            resultMap.put("message", Message.CONFIRM_NOT_FOUND);
            return resultMap;
        }

        List<ConfirmListResDto> list = new ArrayList<>();
        for(DonationConfirm confirm : confirms) {
            ConfirmListResDto confirmListResDto = ConfirmListResDto.builder()
                    .donationConfirmId(confirm.getDonationConfirmId())
                    .title(confirm.getTitle())
                    .name(memberService.getMemberName(confirm.getMemberId()))
                    .createDate(confirm.getCreateDate()).build();

            list.add(confirmListResDto);
        }

        resultMap.put("message", Message.CONFIRM_FIND_SUCCESS);
        resultMap.put("confirm", list);
        resultMap.put("totalPage", confirms.getTotalPages());
        return resultMap;
    }
}
