package org.example.ruiji.controller;


import lombok.extern.slf4j.Slf4j;
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

}
