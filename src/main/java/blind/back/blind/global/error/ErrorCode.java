package blind.back.blind.global.error;

public enum ErrorCode {
    DOUBLE_TOKEN_PRESENT(4040, "토큰이 두 개 존재합니다. 하나만 보내주세요."),
    NOT_FOUND_POST(4040, "삭제되었거나 존재하지 않는 게시물입니다. 다시 확인 부탁드립니다."),
    NOT_FOUND_USER(4040, "존재하지 않는 유저입니다. 다시 확인 부탁드립니다."),
    NOT_FOUND_COMMENT(4040, "삭제되었거나 존재하지 않는 댓글입니다. 확인 부탁드립니다."),
    BLANK_TITLE(4000,"제목이 비어있습니다. 다시 요청부탁드립니다."),
    BLANK_CONTENT(4000,"내용이 비어있습니다. 다시 요청부탁드립니다.");


    private final int code;
    private final String message;

    ErrorCode(final int code, final String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public int getCode() {
        return code;
    }
}
