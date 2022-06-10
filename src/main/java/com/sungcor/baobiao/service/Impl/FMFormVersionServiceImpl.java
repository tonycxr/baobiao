package com.sungcor.baobiao.service.Impl;

import com.sungcor.baobiao.entity.FMFormVersion;
import com.sungcor.baobiao.mapper.FMFormVersionMapper;
import com.sungcor.baobiao.service.IFMFormVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FMFormVersionServiceImpl implements IFMFormVersionService {
    @Autowired
    private FMFormVersionMapper formVersionMapper;
    public FMFormVersion getFMFormVersion(Integer versionID) {
        return formVersionMapper.getFMFormVersion(versionID);
    }
}
