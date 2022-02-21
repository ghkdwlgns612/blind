package blind.back.blind.global.error.Exception;

import blind.back.blind.global.error.BusinessException;
import blind.back.blind.global.error.ErrorCode;

public class BlankContentException extends BusinessException {
    public BlankContentException() {
        super(ErrorCode.BLANK_CONTENT);
    }
}
