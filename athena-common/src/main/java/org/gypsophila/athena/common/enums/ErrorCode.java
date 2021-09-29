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

package org.gypsophila.athena.common.enums;

import lombok.Getter;

public enum ErrorCode {
    /**
     * common code
     */
    SUCCESS(0, "success"),
    FAIL(-1, "fail"),
    
    /**
     * Error code.
     */
    SYSTEM_ERROR(10000, "system error"),
    
    PARAM_ERROR(10001, "param error"),
    
    NAMESPACE_NULL(20000, "namespace cannot be null"),
    
    SERVICE_NAME_NULL(20001, "serviceName cannot be null"),
    
    IP_NULL(20002, "ip  cannot  be null"),
    
    PORT_INVALID(20003, "this port is invalid");
    
    @Getter
    private Integer code;
    
    @Getter
    private String message;
    
    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
