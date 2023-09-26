package org.example.ruiji.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.example.ruiji.common.R;
import org.example.ruiji.dto.DishDto;
import org.example.ruiji.entity.Category;
import org.example.ruiji.entity.Dish;
import org.example.ruiji.service.ICategoryService;
import org.example.ruiji.service.IDishFlavorService;
import org.example.ruiji.service.IDishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private IDishService iDishService;

    @Autowired
    private IDishFlavorService iDishFlavorService;

    @Autowired
    private ICategoryService iCategoryService;

    /**
     * 新增菜品
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
        log.info("接收到的数据是：{}",dishDto);
        iDishService.saveWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }

    /**
     * 在菜品管理中根据菜单id找到对应的菜单名字，将其会回显在页面上
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> pageR(int page,int pageSize,String name){
        //构造分页对象
        //<Dish>中泛型是返回值的类型
        Page<Dish> dishPage = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>(page, pageSize);

        //构造查询对象
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<Dish>()
                .like(name != null, Dish::getName, name)
                .orderByDesc(Dish::getUpdateTime);
        //执行分页查询
        iDishService.page(dishPage,dishLambdaQueryWrapper);
        //忽略Records的对拷
        BeanUtils.copyProperties(dishPage,dishDtoPage,"records");


        //实现CategoryID向CategoryName的映射
        List<Dish> records = dishPage.getRecords();
        List<DishDto> list= records.stream().map((item)->{
            //相当于重新设计list中的item
            DishDto dishDto = new DishDto();
            //对拷基本信息
            BeanUtils.copyProperties(item,dishDto);
            //获取CategoryId
            Long categoryId = item.getCategoryId();
            //查询CategoryId对相应的CategoryName
            Category category = iCategoryService.getById(categoryId);
            String categoryName = category.getName();
            dishDto.setCategoryName(categoryName);
            //返回处理过后的数据
            return dishDto;
        }).collect(Collectors.toList());
        //将改造好的Records数据重新添加到分页对象中
        dishDtoPage.setRecords(list);


        return R.success(dishDtoPage);
    }

    /**
     * 在根据id查询菜品信息和对应的口味信息时，回显相关信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> showBack(@PathVariable Long id){
        DishDto dishDto = iDishService.showBack(id);
        return R.success(dishDto);
    }

    /**
     * 更新修改的信息
     * @param dishDto
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){
        iDishService.updateWithFlavor(dishDto);
        return R.success("修改菜品信息成功");
    }
}
