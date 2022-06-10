package com.sungcor.baobiao.service;

import com.sungcor.baobiao.entity.FMFormArea;
import com.sungcor.baobiao.entity.FMFormInstance;
import com.sungcor.baobiao.entity.FormAccessControl;

import java.util.List;

public interface IOperateFormService {
    public List<FMFormArea> showFormByControl(FormAccessControl businessFlag, Integer formVersioinId) throws Exception;

    public FMFormInstance getFormFieldValueById(Integer processInstanceID) throws Exception;
}
