package org.example.ruiji.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.example.ruiji.common.R;
import org.example.ruiji.entity.Category;
import org.example.ruiji.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

}
