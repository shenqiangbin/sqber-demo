package com.sqber.iyou.controller;

import com.sqber.commonTool.ListUtil;
import com.sqber.commonTool.excel.CommonExcel;
import com.sqber.commonTool.excel.SaveResult;
import com.sqber.commonTool.myenum.IEnum;
import com.sqber.commonWeb.R;
import com.sqber.iyou.config.SomeConf;
import com.sqber.iyou.model.SaleModel;
import com.sqber.iyou.myenum.CheckInfoSyncState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class QuanController {

    @Autowired
    SomeConf someConf;

//    @GetMapping("/")
//    public String index() {
//        return "spring-log4j-demo";
//    }
}
