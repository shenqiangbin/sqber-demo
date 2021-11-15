package com.sqber.clientDemo.controller;

import com.sqber.commonTool.JSONUtil;
import com.sqber.commonTool.ListUtil;
import com.sqber.commonTool.excel.CommonExcel;
import com.sqber.commonTool.excel.ExcelValiException;
import com.sqber.commonTool.excel.SaveResult;
import com.sqber.commonTool.myenum.IEnum;
import com.sqber.log4jdemo.model.SaleModel;
import com.sqber.commonWeb.Resp;
import com.sqber.clientDemo.myenum.CheckInfoSyncState;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/testFile")
    public Resp testFile() throws IOException, IllegalAccessException, InstantiationException {
        CommonExcel commonExcel = createCommonExcel();
        SaveResult saveResult = commonExcel.handle();

        if (!saveResult.isSuccess()) {
            return Resp.error(saveResult.getMsg());
        }

        List<List<String>> data = saveResult.getData();
        List<SaleModel> list = ListUtil.toList(data, SaleModel.class);

        return Resp.success(JSONUtil.toString(list));

    }

    private CommonExcel createCommonExcel() {
        return CommonExcel.create("D:\\code\\TPI\\大数据产品\\贵州大数据项目\\说明\\示例文件\\点排名地图测试数据.xlsx", new CommonExcel.IResultHandler() {
            @Override
            public void validateFirstRow(String sheetName, List<String> rowVal) throws ExcelValiException {
                // 这里是验证表头的逻辑
                if (rowVal.size() != 4) {
                    throw new ExcelValiException("表头和模板不符");
                }

                boolean ok = rowVal.get(0).equals("地区") &&
                        rowVal.get(1).equals("销售额") &&
                        rowVal.get(2).equals("经度") &&
                        rowVal.get(3).equals("维度");

                if (!ok) {
                    throw new ExcelValiException("表头和模板不符");
                }
            }

            @Override
            public boolean store(List<String> rowVal, int rowIndex, int totalRow) {
                return true;
            }
        });
    }

    @GetMapping("/testFile2")
    public Resp testFile2() throws IOException, IllegalAccessException, InstantiationException {

        List<List<String>> data2 = new ArrayList<>();

        CommonExcel commonExcel = createCommonExcel2(data2);
        SaveResult saveResult = commonExcel.handle();

        if (!saveResult.isSuccess()) {
            return Resp.error(saveResult.getMsg());
        }

        List<List<String>> data = saveResult.getData();
        boolean same = data.equals(data2);
        List<SaleModel> list = ListUtil.toList(data2, SaleModel.class);

        return Resp.success(JSONUtil.toString(list));

    }

    private CommonExcel createCommonExcel2(List<List<String>> data) {
        return CommonExcel.create("D:\\code\\TPI\\大数据产品\\贵州大数据项目\\说明\\示例文件\\点排名地图测试数据.xlsx", new CommonExcel.IResultHandler() {
            @Override
            public void validateFirstRow(String sheetName, List<String> rowVal) throws ExcelValiException {
                // 这里是验证表头的逻辑
                if (rowVal.size() != 4) {
                    throw new ExcelValiException("表头和模板不符");
                }

                boolean ok = rowVal.get(0).equals("地区") &&
                        rowVal.get(1).equals("销售额") &&
                        rowVal.get(2).equals("经度") &&
                        rowVal.get(3).equals("维度");

                if (!ok) {
                    throw new ExcelValiException("表头和模板不符");
                }
            }

            @Override
            public boolean store(List<String> rowVal, int rowIndex, int totalRow) {
                data.add(rowVal);
                System.out.println("当前进度：" + rowIndex + "/" + totalRow);
                return true;
            }
        });
    }

}
