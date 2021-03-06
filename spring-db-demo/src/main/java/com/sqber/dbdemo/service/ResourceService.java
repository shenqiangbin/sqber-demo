package com.sqber.dbdemo.service;

import com.sqber.commonTool.MyBeanUtil;
import com.sqber.commonTool.db.model.PageModel;
import com.sqber.dbdemo.model.Resource;
import com.sqber.dbdemo.model.request.ResourcePageRequest;
import com.sqber.dbdemo.model.request.ResourceSaveRequest;
import com.sqber.dbdemo.myenum.RecordStatus;
import com.sqber.dbdemo.repository.ResourceRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ResourceService {

    private ResourceRepository resourceRepository;

    public ResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public PageModel<Resource> getPageList(ResourcePageRequest request) {
        return resourceRepository.getPageList(request);
    }

    public long add(Resource model) {
        model.setCreateTime(new Date());
        model.setCreateUser("createUser");
        model.setStatus(RecordStatus.EXISTS.getVal());
        return resourceRepository.add(model);
    }

    public void save(ResourceSaveRequest request) {
        Resource model = MyBeanUtil.trans(request, Resource.class);

        model.setModifyTime(new Date());
        model.setModifyUser("updateUser");

        resourceRepository.save(model);
    }

    public long remove(int[] ids) {
        return resourceRepository.remove(ids);
    }
}
