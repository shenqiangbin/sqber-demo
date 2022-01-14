package com.sqber.dbdemo.service;

import com.sqber.commonTool.db.model.PageModel;
import com.sqber.dbdemo.model.Resource;
import com.sqber.dbdemo.model.request.ResourcePageRequest;
import com.sqber.dbdemo.repository.ResourceRepository;
import org.springframework.stereotype.Service;

@Service
public class ResourceService {

    private ResourceRepository resourceRepository;

    public ResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public PageModel<Resource> getPageList(ResourcePageRequest request) {
        return resourceRepository.getPageList(request);
    }
}
