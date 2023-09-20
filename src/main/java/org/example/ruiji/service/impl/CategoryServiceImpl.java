package org.example.ruiji.service.impl;

import org.example.ruiji.entity.Category;
import org.example.ruiji.mapper.CategoryMapper;
import org.example.ruiji.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜品及套餐分类 服务实现类
 * </p>
 *
 * @author 崔淼
 * @since 2023-09-20
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

}
