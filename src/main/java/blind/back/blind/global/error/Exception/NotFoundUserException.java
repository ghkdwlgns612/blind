package blind.back.blind.global.error.Exception;

import blind.back.blind.global.error.BusinessException;
import blind.back.blind.global.error.ErrorCode;

public class NotFoundUserException extends BusinessException {

    public NotFoundUserException() {super(ErrorCode.NOT_FOUND_USER);}
}
