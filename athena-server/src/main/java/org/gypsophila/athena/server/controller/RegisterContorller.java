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

package org.gypsophila.athena.server.controller;

import org.gypsophila.athena.common.pojo.Response;
import org.gypsophila.athena.common.util.ResponseUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lixiaoshuang
 */
@RestController
@RequestMapping(path = "v1/register/center")
public class RegisterContorller {
    
    
    @PostMapping(path = "instace")
    public Response<Void> register(HttpServletRequest httpServletRequest) {
        return ResponseUtil.success();
    }
}
