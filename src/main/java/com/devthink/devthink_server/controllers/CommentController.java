package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.application.CommentService;
import com.devthink.devthink_server.common.Error;
import com.devthink.devthink_server.common.ErrorMessage;
import com.devthink.devthink_server.domain.Comment;
import com.devthink.devthink_server.domain.Review;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.dto.CommentRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * 모든 Comment를 조회합니다.
     * @return DB 상에 존재하는 모든 Comment 리스트
     */
    @GetMapping
    public List<Comment> getComments() {
        return commentService.getComments();
    }


    /**
     * 특정 사용자의 Comment를 조회합니다.
     * @return 특정 사용자가 작성한 Comment 리스트
     */
    @GetMapping("/user/{userIdx}")
    public List<Comment> getUserComments(@PathVariable("userIdx") Long userIdx) {
        return commentService.getUserComments(userIdx);
    }

    /**
     * 특정 Post의 Comment를 조회합니다.
     * @return 특정 게시물에 작성된 Comment 리스트
     */
    @GetMapping("/post/{postIdx}")
    public List<Comment> getPostComments(@PathVariable("postIdx") Long postIdx) {
        return commentService.getPostComments(postIdx);
    }

    /**
     * 입력된 comment 정보로 새로운 Comment를 생성합니다.
     * @return 생성된 Comment의 id 값
     */
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public String createComment(@RequestBody CommentRequestDto commentRequestDto){
        User user = null; //TODO: user_id 로 User 가져오기
        Review review = null; //TODO: review_id 로 Review 가져오기
        return commentService.createComment(user, review, commentRequestDto);
    }


    /**
     * commentId를 통하여 기존의 Comment를 수정합니다.
     * @return Comment 수정 결과 response
     */
    @PatchMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updateComment(@PathVariable("commentId") Long commentId, @RequestBody CommentRequestDto commentRequestDto){
        String content = commentRequestDto.getContent();
        if (content.isBlank())      // 입력받은 request에서 content가 공란인지 확인합니다.
            return ResponseEntity.badRequest().body(new Error(HttpStatus.BAD_REQUEST, ErrorMessage.COMMENT_CONTENT_EMPTY));

        Optional<Comment> comment = commentService.getComment(commentId);
        if (comment.isEmpty()) {    // DB에 request 받은 해당 Comment가 존재하는지 확인합니다.
            return ResponseEntity.badRequest().body(new Error(HttpStatus.BAD_REQUEST, ErrorMessage.NO_SUCH_COMMENT));
        } else {
            commentService.updateComment(comment.get(), content);
            return ResponseEntity.ok().body(comment.get().toCommentResponseDto());
        }
    }
}
