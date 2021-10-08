/*
 * Copyright 2021 Gypsophila open source organization.
 *
 * Licensed under the Apache License,Version2.0(the"License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gypsophila.athena.server.handler;

import org.gypsophila.athena.common.enums.ErrorCode;
import org.gypsophila.athena.common.exception.AthenaException;
import org.gypsophila.athena.common.pojo.Response;
import org.gypsophila.athena.common.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lixiaoshuang
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    /**
     * Handling Exception
     *
     * @param httpServletRequest httpServletRequest
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Response exceptionHandler(HttpServletRequest httpServletRequest, Exception e) {
        logger.error("system error:", e);
        return ResponseUtil.fail(ErrorCode.SYSTEM_ERROR.getCode(), e.getMessage());
    }
    
    /**
     * Handling AthenaException
     *
     * @param httpServletRequest httpServletRequest
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = AthenaException.class)
    public Response businessExceptionHandler(HttpServletRequest httpServletRequest, AthenaException e) {
        logger.info("business error,code:" + e.getErrorCode() + "msg:" + e.getErrorMessage());
        return ResponseUtil.fail(ErrorCode.SYSTEM_ERROR.getCode(), e.getMessage());
    }
}
