package com.sqber.dbdemo.controller;

import com.sqber.commonTool.MyBeanUtil;
import com.sqber.commonTool.db.model.PageModel;
import com.sqber.commonWeb.R;
import com.sqber.dbdemo.model.Resource;
import com.sqber.dbdemo.model.request.ResourceAddRequest;
import com.sqber.dbdemo.model.request.ResourcePageRequest;
import com.sqber.dbdemo.model.response.ResourcePageResponse;
import com.sqber.dbdemo.service.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("resource")
@Validated
@Slf4j
public class ResourceController {

    private ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    // 查增改删的顺序
    @GetMapping("getList")
    public R getList(ResourcePageRequest request) {
        PageModel<Resource> dataList = resourceService.getPageList(request);
        PageModel<ResourcePageResponse> result = MyBeanUtil.transPage(dataList, ResourcePageResponse.class);
        ResourcePageResponse.setCategoryStrVal(result.getList());
        return R.success(result);
    }

    @PostMapping("add")
    public R add(@Valid ResourceAddRequest request) {
        Resource model = MyBeanUtil.trans(request, Resource.class);
        long id = resourceService.add(model);
        log.error("the id is :", id);
        return R.success(id);
    }
//
//    @PostMapping("save")
//    public R save(@Valid MenuSaveRequest request) throws IOException {
//        Menu menu = BeanUtils.trans(request, Menu.class);
//        menuService.save(menu);
//        return R.success();
//    }
//
//    @PostMapping("remove")
//    public R save(@NotBlank(message = "ids不能为空") String ids) throws IOException {
//        String[] split = ids.split(",");
//        int[] intIds = Arrays.stream(split).mapToInt(Integer::parseInt).toArray();
//        menuService.remove(intIds);
//        return R.success();
//    }
}
