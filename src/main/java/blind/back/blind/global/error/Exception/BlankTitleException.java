package blind.back.blind.global.error.Exception;

import blind.back.blind.global.error.BusinessException;
import blind.back.blind.global.error.ErrorCode;

public class BlankTitleException extends BusinessException {
    public BlankTitleException() {
        super(ErrorCode.BLANK_TITLE);
    }
}
