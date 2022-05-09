package com.ssafy.helpus.donation.service.Impl;

import com.ssafy.helpus.donation.dto.Comment.CommentReqDto;
import com.ssafy.helpus.donation.entity.Comment;
import com.ssafy.helpus.donation.enumClass.CommentStatus;
import com.ssafy.helpus.donation.repository.CommentRepository;
import com.ssafy.helpus.donation.service.CommentService;
import com.ssafy.helpus.utils.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public Map<String, Object> registerComment(CommentReqDto commentDto, Long memberId) throws Exception {
        log.info("DonationService registerDonation call");

        Map<String, Object> resultMap = new HashMap<>();

        Long boardId = commentDto.getBoardId();
        Long parentId = commentDto.getParentCommentId();
        CommentStatus category = CommentStatus.valueOf(commentDto.getCategory());
        int group = 0, depth = 0;

        if(parentId == null) {
            List<Comment> comments = commentRepository.findByBoardIdAndCategoryAndDepthOrderByCommentGroupDesc(boardId, category, 0);
            if(!comments.isEmpty()) {
                group = comments.get(0).getCommentGroup();
            }
            group++;
        }else {
            group = commentRepository.findById(parentId).get().getCommentGroup();
            depth = commentRepository.findTopByBoardIdAndCategoryAndCommentGroupOrderByDepthDesc(boardId, category, group).get().getDepth() +1;
        }

        Comment comment = Comment.builder()
                .boardId(boardId)
                .category(category)
                .memberId(memberId)
                .content(commentDto.getContent())
                .parentCommentId(parentId)
                .commentGroup(group)
                .depth(depth).build();

        commentRepository.save(comment);

        resultMap.put("message", Message.COMMENT_REGISTER_SUCCESS);
        return resultMap;
    }
}
