package org.example.ruiji.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.example.ruiji.common.R;
import org.example.ruiji.entity.Category;
import org.example.ruiji.service.ICategoryService;
import org.example.ruiji.service.impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 菜品及套餐分类 前端控制器
 * </p>
 *
 * @author 崔淼
 * @since 2023-09-20
 */
@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private ICategoryService iCategoryService;

    @PostMapping
    public R<String> save(@RequestBody Category category){
        log.info("获得的菜单信息为：{}",category);
        iCategoryService.save(category);
        return R.success("添加菜单信息成功");
    }


    @GetMapping("/page")
    public R<Page<Category>> getInfo(Integer page,Integer pageSize,String name){
        //分页构造器
        Page<Category> categoryPage = new Page<>(page, pageSize);
        //条件构造器
        LambdaQueryWrapper<Category> categoryLambdaQueryWrapper = new LambdaQueryWrapper<Category>()
                .orderByAsc(Category::getSort);
        //执行查询
        iCategoryService.page(categoryPage,categoryLambdaQueryWrapper);
        return R.success(categoryPage);
    }

    /**
     * 删除菜品分类，除非和Dish表、Setmeal表关联
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(Long ids){
        log.info("根据id删除菜品分类");
//        iCategoryService.removeById(id);
        iCategoryService.select(ids);
        return R.success("删除成功");
    }


    @PutMapping
    public R<String> update(@RequestBody Category category){
        log.info("更新菜品分类的信息");
        iCategoryService.updateById(category);
        return R.success("更新成功");
    }

    @GetMapping("/list")
    public R<List<Category>> listCategory(Category category){
        log.info("在添加菜品页面的下拉框中展示可选的菜品分类");
        LambdaQueryWrapper<Category> categoryLambdaQueryWrapper = new LambdaQueryWrapper<Category>()
                .eq(category.getType() != null, Category::getType, category.getType())
                .orderByAsc(Category::getSort)
                .orderByDesc(Category::getUpdateTime);

        List<Category> list = iCategoryService.list(categoryLambdaQueryWrapper);
        return R.success(list);

    }
}
