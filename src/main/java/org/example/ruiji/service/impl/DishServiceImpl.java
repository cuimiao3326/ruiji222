package org.example.ruiji.service.impl;

import org.example.ruiji.entity.Dish;
import org.example.ruiji.mapper.DishMapper;
import org.example.ruiji.service.IDishService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
