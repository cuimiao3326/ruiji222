package org.example.ruiji.service;

import org.example.ruiji.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 菜品及套餐分类 服务类
 * </p>
 *
 * @author 崔淼
 * @since 2023-09-20
 */
public interface ICategoryService extends IService<Category> {
    void select(Long id);

}
