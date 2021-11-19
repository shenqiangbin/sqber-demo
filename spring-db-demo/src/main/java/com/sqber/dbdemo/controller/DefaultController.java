package com.sqber.dbdemo.controller;

import com.sqber.commonTool.ListUtil;
import com.sqber.commonTool.excel.CommonExcel;
import com.sqber.commonTool.excel.SaveResult;
import com.sqber.commonTool.myenum.IEnum;
import com.sqber.dbdemo.model.SaleModel;
import com.sqber.commonWeb.R;
import com.sqber.dbdemo.myenum.CheckInfoSyncState;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class DefaultController {

    @GetMapping("/")
    public String index() {
        return "spring-db-demo";
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

    @GetMapping("/testFile")
    public R testFile() throws IOException, IllegalAccessException, InstantiationException {

        String filePath = "D:\\code\\TPI\\大数据产品\\贵州大数据项目\\说明\\示例文件\\点排名地图测试数据.xlsx";
        CommonExcel commonExcel = CommonExcel.create(filePath, new String[]{"地区", "销售额", "经度", "维度"});
        SaveResult saveResult = commonExcel.handle();

        if (!saveResult.isSuccess()) {
            return R.warn(saveResult.getMsg());
        }

        List<List<String>> data = saveResult.getData();
        List<SaleModel> list = ListUtil.toList(data, SaleModel.class);

        return R.success(list);

    }


    @GetMapping("/testFile2")
    public R testFile2() throws IOException, IllegalAccessException, InstantiationException {

        List<List<String>> data2 = new ArrayList<>();

        CommonExcel commonExcel = createCommonExcel2(data2);
        SaveResult saveResult = commonExcel.handle();

        if (!saveResult.isSuccess()) {
            return R.error(saveResult.getMsg());
        }

        List<List<String>> data = saveResult.getData();
        boolean same = data.equals(data2);
        List<SaleModel> list = ListUtil.toList(data2, SaleModel.class);

        return R.success(list);

    }

    private CommonExcel createCommonExcel2(List<List<String>> data) {
        return CommonExcel.create(
                "D:\\code\\TPI\\大数据产品\\贵州大数据项目\\说明\\示例文件\\点排名地图测试数据.xlsx",
                new String[]{"地区", "销售额", "经度", "维度"},
                (rowVal, rowIndex, totalRow) -> {
                    data.add(rowVal);
                    System.out.println("当前进度：" + rowIndex + "/" + totalRow);
                    return true;
                });
    }

}
