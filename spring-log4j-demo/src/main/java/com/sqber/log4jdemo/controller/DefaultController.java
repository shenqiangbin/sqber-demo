package com.sqber.log4jdemo.controller;

import com.sqber.commonTool.myenum.IEnum;
import com.sqber.commonWeb.BaseResponse;
import com.sqber.log4jdemo.myenum.CheckInfoSyncState;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class DefaultController {

    @GetMapping("/")
    public String index() {
        return "log index";
    }

    @GetMapping("/test")
    public BaseResponse test() {
        return BaseResponse.error("数据库连接失败");
    }

    @GetMapping("/test1")
    public BaseResponse test1() {
        return BaseResponse.success("this is msg");
    }

    @GetMapping("/test2")
    public BaseResponse test2() {
        return BaseResponse.warn("id值不能为空");
    }

    /**
     * 里面会抛 RunTimeException 的
     *
     * @return
     */
    @GetMapping("/user")
    public BaseResponse getUser(String id) {
        int i = 0;
        int result = 5 / i;
        return BaseResponse.success("tom");
    }

    /**
     * 一种是非运行时异常的
     *
     * @return
     * @throws IOException
     */
    @GetMapping("/user2")
    public BaseResponse getUser2() throws IOException {
        throw new IOException("cannot read file");
        //return BaseResponse.success("tom");
    }


    @GetMapping("/testEnum")
    public BaseResponse testEnum() {
        CheckInfoSyncState state = IEnum.getEnumByVal(CheckInfoSyncState.class, 30);
        int val = state.getValue();

        return BaseResponse.success(val);
    }

}
