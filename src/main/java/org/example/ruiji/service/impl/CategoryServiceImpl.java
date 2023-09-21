package org.example.ruiji.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.example.ruiji.common.DuplicateException;
import org.example.ruiji.entity.Category;
import org.example.ruiji.entity.Dish;
import org.example.ruiji.entity.Setmeal;
import org.example.ruiji.mapper.CategoryMapper;
import org.example.ruiji.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.ruiji.service.IDishService;
import org.example.ruiji.service.ISetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜品及套餐分类 服务实现类
 * </p>
 *
 * @author 崔淼
 * @since 2023-09-20
 */
@Slf4j
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    @Autowired
    private IDishService iDishService;
    @Autowired
    private ISetmealService iSetmealService;
    @Autowired
    private ICategoryService iCategoryService;

    @Override
    public void select(Long ids) {
        Integer count = iDishService.lambdaQuery()
                .eq(Dish::getCategoryId, ids)
                .count();

        log.info("一共查询到：{}条菜单关联数据",count);
        if (count>0){
            //已经关联了菜品，抛出一个业务异常
            throw new DuplicateException("当前分类下关联了菜品，不能删除");
        }

        Integer count1 = iSetmealService.lambdaQuery()
                .eq(Setmeal::getCategoryId, ids)
                .count();
        log.info("一共查询到：{}条套餐关联数据",count1);
        if (count1>0){
            //已经关联了套餐，抛出了一个业务异常
            throw new DuplicateException("当前分类关联了套餐，不能删除");
        }

        //正常删除分类
        iCategoryService.removeById(ids);

    }
}

