package blind.back.blind.global.error;

import blind.back.blind.global.error.Exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(DoubleTokenException.class)
    protected ResponseEntity<ErrorResponse> handleDoubleTokenException(DoubleTokenException e) {
        log.error("handleDoubleTokenException", e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.DOUBLE_TOKEN_PRESENT);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(NotFoundPostException.class)
    protected ResponseEntity<ErrorResponse> handleNotFoundPostException(NotFoundPostException e) {
        log.error("삭제되었거나 존재하지 않는 게시물입니다.", e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.NOT_FOUND_POST);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(NotFoundUserException.class)
    protected ResponseEntity<ErrorResponse> handleNotFoundUserException(NotFoundUserException e) {
        log.error("유저가 올바르지 않습니다.", e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.NOT_FOUND_USER);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(NotFoundCommentException.class)
    protected ResponseEntity<ErrorResponse> handleNotFoundCommentException(NotFoundCommentException e) {
        log.error("존재하지 않거나 삭제된 댓글입니다.", e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.NOT_FOUND_COMMENT);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @ExceptionHandler(BlankTitleException.class)
    protected ResponseEntity<ErrorResponse> handleBlankTitleException(BlankTitleException e) {
        log.error("제목이 비어있습니다.", e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.BLANK_TITLE);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(BlankContentException.class)
    protected ResponseEntity<ErrorResponse> handleBlankContentException(BlankContentException e) {
        log.error("내용이 비어있습니다.", e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.BLANK_CONTENT);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
