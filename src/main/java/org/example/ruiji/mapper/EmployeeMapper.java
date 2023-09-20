package org.example.ruiji.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.ruiji.entity.Employee;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
    //继承BaseMapper类中的crud方法
}
