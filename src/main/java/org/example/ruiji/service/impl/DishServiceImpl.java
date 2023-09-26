package org.example.ruiji.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.example.ruiji.dto.DishDto;
import org.example.ruiji.entity.Dish;
import org.example.ruiji.entity.DishFlavor;
import org.example.ruiji.mapper.DishMapper;
import org.example.ruiji.service.IDishFlavorService;
import org.example.ruiji.service.IDishService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜品管理 服务实现类
 * </p>
 *
 * @author 崔淼
 * @since 2023-09-21
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements IDishService {

    @Autowired
    private IDishFlavorService iDishFlavorService;
    @Override
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {

        //保存菜品的基本信息到菜品表Dish
        this.save(dishDto);
        //获取菜品id
        Long id = dishDto.getId();
        //为每个口味添加所对应的菜单id
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().peek((item)-> item.setDishId(id)).collect(Collectors.toList());
        //保存口味数据到菜单口味表dish_flavor
        iDishFlavorService.saveBatch(flavors);
    }

    /**
     * 在修改页面实现数据回显
     * @param id
     * @return
     */
    @Override
    public DishDto showBack(Long id) {
        //根据id查询菜品
        Dish dish = this.getById(id);

        DishDto dishDto = new DishDto();

        //实现数据对拷
        BeanUtils.copyProperties(dish,dishDto);

        //条件构造器
        LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<DishFlavor>()
                .eq(DishFlavor::getDishId, id);
        //获取风味信息
        List<DishFlavor> flavorList = iDishFlavorService.list(wrapper);

        //将风味信息添加到将要回显的数据中
        dishDto.setFlavors(flavorList);

        return dishDto;
    }

    /**
     * 获取更新后的信息，然后对数据库进行更新操作
     * @param dishDto
     */
    @Override
    public void updateWithFlavor(DishDto dishDto) {
        //更新dish表的基本信息
        this.updateById(dishDto);
        //清理当前菜品对应的口味数据
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<DishFlavor>()
                .eq(DishFlavor::getDishId, dishDto.getId());

        iDishFlavorService.remove(queryWrapper);
        //添加当前提交过来的口味数据
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().peek((item)-> item.setDishId(dishDto.getId())).collect(Collectors.toList());

        iDishFlavorService.saveBatch(flavors);

    }

}
