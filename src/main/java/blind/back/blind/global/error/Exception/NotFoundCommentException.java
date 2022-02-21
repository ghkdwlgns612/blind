package blind.back.blind.global.error.Exception;

import blind.back.blind.global.error.BusinessException;
import blind.back.blind.global.error.ErrorCode;

public class NotFoundCommentException extends BusinessException {

    public NotFoundCommentException() {super(ErrorCode.NOT_FOUND_COMMENT);}
}
