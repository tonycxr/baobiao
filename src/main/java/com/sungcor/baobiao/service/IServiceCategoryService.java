/**
 * 
 */
package com.sungcor.baobiao.service;


import com.sungcor.baobiao.entity.ServiceCategory;

import java.util.List;

/**
 * @author 袁啸川
 * 2012-5-7
 * 服务产品分类业务层接口
 */

public interface IServiceCategoryService {
	/**
	 * 根据ID获取服务产品分类对象
	 * @param id 
	 * @return
	 */
	public ServiceCategory getCategoryById(int id);
	
	/**
	 * 得到所有的服务产品分类对象
	 * @return
	 */
	public List<ServiceCategory> getAll();
}
