package blind.back.blind.global.error.Exception;

import blind.back.blind.global.error.BusinessException;
import blind.back.blind.global.error.ErrorCode;

public class NotFoundPostException extends BusinessException {

    public NotFoundPostException() {super(ErrorCode.NOT_FOUND_POST);}
}
