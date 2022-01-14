package com.sqber.log4jdemo.controller;

import com.sqber.commonTool.CmdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@RequestMapping("/mysqldump")
public class MySQLDumpController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/")
    public String index() {
        return "mysqldump index";
    }

    @GetMapping("/test")
    public Object test() throws IOException, InterruptedException {
        AtomicBoolean result = new AtomicBoolean(false);
        String cmd = "mysqldump  -uroot -p123456 --port=3306 --skip-add-drop-table --where=\"createtime>='2019-01-02 00:00:00'\" --no-create-info --skip-tz-utc  personMgr --tables menu  --result-file=/home/sql/result.sql";
        CmdUtil.execCmdSync(cmd, (success, exitVal, error, output, originalCmd) -> {
            if (!success) {
                LOGGER.debug("cmd fail", error);
                throw new IOException(error);
            }
            result.set(success);
        });
        return result.get();
    }

    @GetMapping("/test2")
    public Object test2() throws IOException, InterruptedException {
        AtomicBoolean result = new AtomicBoolean(false);
        String cmd = "mysqldump  -uroot -p123456 --port=3306 --skip-add-drop-table --where=\"createtime>='2019-01-02'\" --no-create-info --skip-tz-utc  personMgr --tables menu  --result-file=/home/sql/result.sql";
        CmdUtil.execCmdSync(cmd, (success, exitVal, error, output, originalCmd) -> {
            if (!success) {
                LOGGER.debug("cmd fail", error);
                throw new IOException(error);
            }
            result.set(success);
        });
        return result.get();
    }
}
