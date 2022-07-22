package org.example.common.msg.auth;

import org.example.common.constant.RestCodeConstants;
import org.example.common.msg.BaseResponse;

/**
 * Created by ace on 2017/8/23.
 */
public class TokenErrorResponse extends BaseResponse {
    public TokenErrorResponse(String message) {
        super(RestCodeConstants.TOKEN_ERROR_CODE, message);
    }
}
