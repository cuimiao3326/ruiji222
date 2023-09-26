package org.example.ruiji.service;

import org.example.ruiji.dto.DishDto;
import org.example.ruiji.entity.Dish;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 菜品管理 服务类
 * </p>
 *
 * @author 崔淼
 * @since 2023-09-21
 */
public interface IDishService extends IService<Dish> {

    void saveWithFlavor(DishDto dishDto);

    DishDto showBack(Long id);

    void updateWithFlavor(DishDto dishDto);
}
