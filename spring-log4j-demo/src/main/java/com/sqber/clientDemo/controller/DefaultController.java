package com.sqber.clientDemo.controller;

import com.sqber.commonTool.myenum.IEnum;
import com.sqber.commonWeb.Resp;
import com.sqber.clientDemo.myenum.CheckInfoSyncState;
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
    public Resp test() {
        return Resp.error("数据库连接失败");
    }

    @GetMapping("/test1")
    public Resp test1() {
        return Resp.success("this is msg");
    }

    @GetMapping("/test2")
    public Resp test2() {
        return Resp.warn("id值不能为空");
    }

    /**
     * 里面会抛 RunTimeException 的
     *
     * @return
     */
    @GetMapping("/user")
    public Resp getUser(String id) {
        int i = 0;
        int result = 5 / i;
        return Resp.success("tom");
    }

    /**
     * 一种是非运行时异常的
     *
     * @return
     * @throws IOException
     */
    @GetMapping("/user2")
    public Resp getUser2() throws IOException {
        throw new IOException("cannot read file");
        //return Resp.success("tom");
    }


    @GetMapping("/testEnum")
    public Resp testEnum() {
        CheckInfoSyncState state = IEnum.getEnumByVal(CheckInfoSyncState.class, 30);
        int val = state.getValue();

        return Resp.success(val);
    }

}
