package com.pei.mybatis_test.controller.config;

import com.pei.mybatis_test.mybatis_plus.config.Result;
import com.pei.mybatis_test.mybatis_plus.config.ResultStatusCode;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName FinalExceptionHandlerController
 * @Deacription
 * @Author peipei
 * @Date 2020/6/4 21:08
 * @Version 1.0
 **/
@RestController
@Slf4j
@Api(tags="错误接口")
public class FinalExceptionHandlerController implements ErrorController {
    @Override
    public String getErrorPath() {
        return "/error";
    }

    @GetMapping(value = "/error")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Object error(HttpServletRequest request, HttpServletResponse response) {

        log.error("response error,httpCode:" + response.getStatus());
        // 错误处理逻辑
        int status = response.getStatus();
        if (status == 404) {
            return new Result(ResultStatusCode.REQUEST_NOT_FOUND, "小伙子你有点调皮哦！(*^▽^*)");
        } else if (status == 500) {
            return new Result(ResultStatusCode.SYSTEM_ERR, "小伙子你麻烦大了！(*^▽^*)");
        } else if (status >= 100 && status < 200) {
            return new Result(ResultStatusCode.HTTP_ERROR_100, null);
        } else if (status >= 300 && status < 400) {
            return new Result(ResultStatusCode.HTTP_ERROR_300, null);
        } else if (status >= 400 && status < 500) {
            return new Result(ResultStatusCode.HTTP_ERROR_400, null);
        } else {
            return new Result(ResultStatusCode.SYSTEM_ERR, "小伙子你麻烦大了！(*^▽^*)");
        }
    }

}