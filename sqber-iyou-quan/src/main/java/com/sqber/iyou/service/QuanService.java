package com.sqber.iyou.service;

import com.sqber.commonTool.CmdUtil;
import com.sqber.commonTool.DateUtil;
import com.sqber.commonTool.JSONUtil;
import com.sqber.iyou.config.SomeConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuanService {

    private final Logger LOG = LoggerFactory.getLogger(QuanService.class);

    @Autowired
    private SomeConf someConf;

    public void start() throws IOException, InterruptedException {
        LOG.debug("start");

        connect();

        //创建定时器对象
        Timer timer = new Timer();
//        Timer timer = new Timer(true);  //设为守护线程模式


        String btn = someConf.getMiddleBtn();
        String middleBtn = someConf.getAdbPath() + " -s " + someConf.getServer2() + " shell input tap " + btn;
        click(middleBtn);

        //指定定时任务s
        timer.schedule(new TimerTask() {
            //编写一个匿名内部类，即定时任务
            @Override
            public void run() {
                System.out.println("timer-" + DateUtil.format(new Date()));

                int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                int minute = Calendar.getInstance().get(Calendar.MINUTE);

                //System.out.println(String.format("hour:%s, minute:%s", hour,minute));

                if (hour == someConf.getHour() && minute == someConf.getMinute()) {
                    timer.cancel();
                    try {
                        go();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


            }
        }, new Date(), 500);
    }


    public void go() throws IOException, InterruptedException {
        String leftButton = someConf.getAdbPath() + " -s " + someConf.getServer2() + " shell input tap " + someConf.getLeftBtn();
        click(leftButton);


        String[] btns = someConf.getQuanBtn().split(",");
        String q1 = someConf.getAdbPath() + " shell input tap 699  346.7";
        String q2 = someConf.getAdbPath() + " shell input tap 699  575.5";
        String q3 = someConf.getAdbPath() + " shell input tap 699  807.4";
        String q4 = someConf.getAdbPath() + " shell input tap 699  1039";
        String q5 = someConf.getAdbPath() + " shell input tap 699  1240";

//        int quanNumber = someConf.getQuanNumber();
//        String str =
//                quanNumber == 1 ? q1 :
//                        quanNumber == 2 ? q2 :
//                                quanNumber == 3 ? q3 :
//                                        quanNumber == 4 ? q4 :
//                                                quanNumber == 5 ? q5 : "";
        String[] quanNumList = someConf.getQuanNumber().split(",");
        List<String> cmdList = getCmdList(quanNumList, btns);

        for (int i = 0; i < 20; i++) {
            System.out.println("click-" + DateUtil.format(new Date()));
            click(cmdList);
            Thread.sleep(10);
        }
    }

    private List<String> getCmdList(String[] quanNumList, String[] btns) {
        return Arrays.stream(quanNumList).map(num ->
                someConf.getAdbPath() + " -s " + someConf.getServer2() + " shell input tap " +
                        btns[Integer.parseInt(num) - 1]
        ).collect(Collectors.toList());

    }

    public void click(List<String> cmdList) throws IOException, InterruptedException {
        for (String cmd : cmdList) {
            click(cmd);
        }
    }

    public void click(String cmd) throws IOException, InterruptedException {
        CmdUtil.execCmdSync(cmd, (success, exitVal, error, output, originalCmd) -> {
            if (!success) {
                throw new IOException(error);
            }
        });
    }


    public void connect() throws IOException, InterruptedException {
        String cmd = someConf.getAdbPath() + " connect " + someConf.getServer();
        CmdUtil.execCmdSync(cmd, (success, exitVal, error, output, originalCmd) -> {
            System.out.println(output);
            System.out.println(error);
        });
    }
}
