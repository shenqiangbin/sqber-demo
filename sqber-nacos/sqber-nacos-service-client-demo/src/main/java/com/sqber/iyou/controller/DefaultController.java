package com.sqber.clientDemo.controller;

import com.sqber.commonTool.myenum.IEnum;
import com.sqber.commonWeb.R;
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
    public R test() {
        return R.error("数据库连接失败");
    }

    @GetMapping("/test1")
    public R test1() {
        return R.success("this is msg");
    }

    @GetMapping("/test2")
    public R test2() {
        return R.warn("id值不能为空");
    }

    /**
     * 里面会抛 RunTimeException 的
     *
     * @return
     */
    @GetMapping("/user")
    public R getUser(String id) {
        int i = 0;
        int result = 5 / i;
        return R.success("tom");
    }

    /**
     * 一种是非运行时异常的
     *
     * @return
     * @throws IOException
     */
    @GetMapping("/user2")
    public R getUser2() throws IOException {
        throw new IOException("cannot read file");
        //return Resp.success("tom");
    }


    @GetMapping("/testEnum")
    public R testEnum() {
        CheckInfoSyncState state = IEnum.getEnumByVal(CheckInfoSyncState.class, 30);
        int val = state.getVal();

        return R.success(val);
    }

}
