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

import org.apache.commons.lang3.StringUtils;
import org.gypsophila.athena.common.enums.ErrorCode;
import org.gypsophila.athena.common.exception.AthenaException;
import org.gypsophila.athena.common.pojo.Instance;
import org.gypsophila.athena.common.pojo.Response;
import org.gypsophila.athena.common.pojo.Service;
import org.gypsophila.athena.common.util.ResponseUtil;
import org.gypsophila.athena.server.register.RegisterCenter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

/**
 * @author lixiaoshuang
 */
@RestController
@RequestMapping(path = "v1/register/center")
public class RegisterController {
    
    
    @PostMapping(path = "add")
    public Response<Void> register(HttpServletRequest httpServletRequest) {
        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
        if (null == parameterMap) {
            return ResponseUtil.fail(ErrorCode.PARAM_ERROR.getCode(), ErrorCode.PARAM_ERROR.getMessage());
        }
        String namespaces = httpServletRequest.getParameter("namespace");
        String serviceName = httpServletRequest.getParameter("serviceName");
        String ip = httpServletRequest.getParameter("ip");
        String port = httpServletRequest.getParameter("port");
        
        this.checkParam(namespaces, serviceName, ip, port);
        
        Service service = new Service(namespaces, serviceName, new Instance(ip, Integer.parseInt(port), true));
        RegisterCenter.single().register(service);
        return ResponseUtil.success();
    }
    
    private void checkParam(String namespaces, String serviceName, String ip, String port) {
        if (StringUtils.isBlank(namespaces)) {
            throw new AthenaException(ErrorCode.NAMESPACE_NULL.getCode(), ErrorCode.NAMESPACE_NULL.getMessage());
        }
        if (StringUtils.isBlank(serviceName)) {
            throw new AthenaException(ErrorCode.SERVICE_NAME_NULL.getCode(), ErrorCode.SERVICE_NAME_NULL.getMessage());
        }
        if (StringUtils.isBlank(ip)) {
            throw new AthenaException(ErrorCode.IP_NULL.getCode(), ErrorCode.IP_NULL.getMessage());
        }
        if (StringUtils.isBlank(port)) {
            throw new AthenaException(ErrorCode.PORT_INVALID.getCode(), ErrorCode.PORT_INVALID.getMessage());
        }
    }
    
    @PostMapping(path = "remote")
    public Response<Void> cancel(HttpServletRequest httpServletRequest) {
        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
        if (null == parameterMap) {
            return ResponseUtil.fail(ErrorCode.PARAM_ERROR.getCode(), ErrorCode.PARAM_ERROR.getMessage());
        }
        String namespaces = httpServletRequest.getParameter("namespace");
        String serviceName = httpServletRequest.getParameter("serviceName");
        String ip = httpServletRequest.getParameter("ip");
        String port = httpServletRequest.getParameter("port");
        
        this.checkParam(namespaces, serviceName, ip, port);
        
        Service service = new Service(namespaces, serviceName, new Instance(ip, Integer.parseInt(port), true));
        RegisterCenter.single().cancel(service);
        return ResponseUtil.success();
    }
    
    
    @PostMapping(path = "get")
    public Response<Set<Instance>> getList(HttpServletRequest httpServletRequest) {
        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
        if (null == parameterMap) {
            return ResponseUtil.fail(ErrorCode.PARAM_ERROR.getCode(), ErrorCode.PARAM_ERROR.getMessage());
        }
        String namespaces = httpServletRequest.getParameter("namespace");
        String serviceName = httpServletRequest.getParameter("serviceName");
        if (StringUtils.isBlank(namespaces)) {
            throw new AthenaException(ErrorCode.NAMESPACE_NULL.getCode(), ErrorCode.NAMESPACE_NULL.getMessage());
        }
        if (StringUtils.isBlank(serviceName)) {
            throw new AthenaException(ErrorCode.SERVICE_NAME_NULL.getCode(), ErrorCode.SERVICE_NAME_NULL.getMessage());
        }
        Set<Instance> instances = RegisterCenter.single().instanceList(namespaces, serviceName);
        return ResponseUtil.success(instances);
    }
}
