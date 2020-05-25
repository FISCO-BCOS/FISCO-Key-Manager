/**
 * Copyright 2014-2020  the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.fisco.bcos.key.mgr.base.exception;

import com.alibaba.fastjson.JSON;
import org.fisco.bcos.key.mgr.base.code.ConstantCode;
import org.fisco.bcos.key.mgr.base.code.RetCode;
import org.fisco.bcos.key.mgr.base.entity.BaseResponse;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * catch an handler exception.
 */
@ControllerAdvice
@Log4j2
public class ExceptionsHandler {

    /**
     * catch：KeyMgrException.
     */
    @ResponseBody
    @ExceptionHandler(value = KeyMgrException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public BaseResponse myExceptionHandler(KeyMgrException nodeMgrException) {
        log.warn("catch business exception", nodeMgrException);
        RetCode retCode = Optional.ofNullable(nodeMgrException).map(KeyMgrException::getRetCode)
            .orElse(ConstantCode.SYSTEM_EXCEPTION);

        BaseResponse bre = new BaseResponse(retCode);
        log.warn("business exception return:{}", JSON.toJSONString(bre));
        return bre;
    }

    /**
     * catch：AccessDeniedException.
     */
    @ResponseBody
    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public BaseResponse accessDeniedExceptionHandler(AccessDeniedException exception)
            throws Exception {
        log.warn("catch accessDenied exception", exception);
        BaseResponse bre = new BaseResponse(ConstantCode.ACCESS_DENIED);
        log.warn("accessDenied exception return:{}", JSON.toJSONString(bre));
        return bre;
    }

    /**
     * catch:paramException
     */
    @ResponseBody
    @ExceptionHandler(value = ParamException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public BaseResponse paramExceptionHandler(ParamException paramException) {
        log.warn("catch param exception", paramException);
        RetCode retCode = Optional.ofNullable(paramException).map(ParamException::getRetCode)
            .orElse(ConstantCode.SYSTEM_EXCEPTION);

        BaseResponse bre = new BaseResponse(retCode);
        log.warn("param exception return:{}", JSON.toJSONString(bre));
        return bre;
    }

    /**
     * catch：RuntimeException.
     */
    @ResponseBody
    @ExceptionHandler(value = RuntimeException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse exceptionHandler(RuntimeException exc) {
        log.warn("catch RuntimeException", exc);
        // 默认系统异常
        RetCode retCode = ConstantCode.SYSTEM_EXCEPTION;

        BaseResponse bre = new BaseResponse(retCode);
        log.warn("system RuntimeException return:{}", JSON.toJSONString(bre));
        return bre;
    }
}