package org.example.ruiji.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.ruiji.entity.DishFlavor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 菜品口味关系表 Mapper 接口
 * </p>
 *
 * @author 崔淼
 * @since 2023-09-22
 */
@Mapper
public interface DishFlavorMapper extends BaseMapper<DishFlavor> {

}
