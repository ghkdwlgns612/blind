package blind.back.blind.global.error.Exception;

import blind.back.blind.global.error.BusinessException;
import blind.back.blind.global.error.ErrorCode;

public class DoubleTokenException extends BusinessException {

    public DoubleTokenException() {
        super(ErrorCode.DOUBLE_TOKEN_PRESENT);
    }
}
