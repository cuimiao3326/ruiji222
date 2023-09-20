package org.example.ruiji.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.ruiji.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 菜品及套餐分类 Mapper 接口
 * </p>
 *
 * @author 崔淼
 * @since 2023-09-20
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}
