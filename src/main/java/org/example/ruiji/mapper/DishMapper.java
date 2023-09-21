package org.example.ruiji.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.ruiji.entity.Dish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 菜品管理 Mapper 接口
 * </p>
 *
 * @author 崔淼
 * @since 2023-09-21
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {

}
