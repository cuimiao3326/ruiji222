package org.example.ruiji.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.example.ruiji.common.R;
import org.example.ruiji.entity.Employee;
import org.example.ruiji.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login(@RequestBody Employee employee, HttpServletRequest request){

        //将页面提交的密码进行md5加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        //根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();//包装查询对象(MYBatis中的方法）(最好使用mybaitsplus)
        queryWrapper.eq(Employee::getUsername,employee.getUsername());//等值查询
        Employee emp = employeeService.getOne(queryWrapper);//因为username在数据库中是Unique的，所以使用getOne方法获取数据
        //如果没有查询到，返回登录失败
        if(emp == null){
            return R.error("未查询到用户名，登录失败");
        }
        //密码对比，如果不一致则返回登录失败
        if(!emp.getPassword().equals(password)){
            return R.error("密码错误，登录失败");
        }
        //查看员工状态，如果已经为禁用状态，则返回员工已禁用的结果
        if(emp.getStatus() == 0){
            return R.error("该员工已经被禁用，无法登录");

        }
        //登陆成功，将员工id存入Session
        request.getSession().setAttribute("employee",emp.getId());

        //并返回登录成功
        return R.success(emp);
    }

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        //清理Session中的用户id
        request.getSession().removeAttribute("employee");
        //返回结果
        return R.success("成功退出");
    }

    @PostMapping
    public R<String> save(HttpServletRequest request,@RequestBody Employee employee){
        log.info("获取的员工信息为：{}",employee);
        //设置初始密码为123456
        String password = DigestUtils.md5DigestAsHex("123456".getBytes());
        employee.setPassword(password);

//        LocalDateTime now = LocalDateTime.now();
//        employee.setCreateTime(now);
//        employee.setUpdateTime(now);
//        employee.setCreateUser((Long) request.getSession().getAttribute("employee"));
//        employee.setUpdateUser((Long) request.getSession().getAttribute("employee"));

        employeeService.save(employee);
        return R.success("新增员工成功");
    }

    @GetMapping("/page")
    public R<Page<Employee>> getInfo(Integer page , Integer pageSize, String name){
        log.info("page{},pageSize{},name{}",page,pageSize,name);

        //构造分页构造器
        Page<Employee> page1 = new Page<Employee>(page, pageSize);

        //下面是mybatis plus的内容
        //构造条件构造器
        LambdaQueryWrapper<Employee> employeeLambdaQueryWrapper = new LambdaQueryWrapper<>();

        //添加过滤条件
        employeeLambdaQueryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
        
        //添加排序条件
        employeeLambdaQueryWrapper.orderByDesc(Employee::getUpdateTime);

        //执行查询
        employeeService.page(page1,employeeLambdaQueryWrapper);


        return R.success(page1);
    }

    @PutMapping
    public R<String> update(HttpServletRequest httpServletRequest,@RequestBody Employee employee){
        log.info("接收到的jason");

        //线程号测试
        long id = Thread.currentThread().getId();
        log.info("ThreadId:{}",id);


//        Long empId = (Long)httpServletRequest.getSession().getAttribute("employee");
//
//        employee.setUpdateTime(LocalDateTime.now());
//
//        employee.setUpdateUser(empId);

        employeeService.updateById(employee);

        return R.success("员工信息修改成功");
    }

    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        log.info("根据id查询员工信息");
        Employee employee = employeeService.getById(id);
        if(employee != null){
            return R.success(employee);
        }
        return R.error("没有查询到信息");
    }

}
