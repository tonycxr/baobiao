package com.sungcor.baobiao.service.Impl;

import com.sungcor.baobiao.entity.DictItem;
import com.sungcor.baobiao.mapper.DictItemMapper;
import com.sungcor.baobiao.service.IDictItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DictItemServiceImpl implements IDictItemService {
    @Autowired
    private DictItemMapper dictItemMapper;

    @Override
    public void deleteDictItemById(int id) {
        DictItem dictItem = getDictItemById(id);
        Integer dictID = dictItem.getDictId();
        dictItemMapper.deleteDictItemById(id);
        List<DictItem> list = dictItemMapper.getDictItems(dictID);

    }

    @Override
    public DictItem getDictItemById(int id) {

            DictItem dictItem = dictItemMapper.getDictItemById(id);

            return dictItem;


    }

    @Override
    public List<DictItem> getDictItemsPaging(Object object) {
        List<DictItem> list = dictItemMapper.getDictItemsPaging(object);
        return list;
    }

    @Override
    public int getDictsItemCount(int id) {
        int count  = Integer.parseInt(dictItemMapper.getDictItemsCount(id).get(0).get("dictItemCount").toString());
        return count;
    }

    @Override
    public void insertDictItem(DictItem dictItem) {
        dictItemMapper.insertDictItem(dictItem);
        List<DictItem> list = dictItemMapper.getDictItems(dictItem.getDictId());
    }

    @Override
    public void updateDictItem(DictItem dictItem) {

        dictItemMapper.updateDictItem(dictItem);

        List<DictItem> list = dictItemMapper.getDictItems(dictItem.getDictId());

    }

    @Override
    public List<DictItem> getDictItems(Object object) {
        return dictItemMapper.getDictItems(object);
    }

    @Override
    public boolean checkCode(String code) {
        DictItem di = dictItemMapper.getDictItemByCode(code);
        if(di==null){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public boolean checkName(Object object) {
        DictItem di = dictItemMapper.getDictItemByName(object);
        if(di==null){
            return true;
        }else{
            return false;
        }
    }

    public List<DictItem> getDictItemByDictCode(String code){
        List<DictItem> list=dictItemMapper.getDictItemByDictCode(code);
        return list;
    }
}
