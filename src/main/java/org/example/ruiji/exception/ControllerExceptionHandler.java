package org.example.ruiji.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.ruiji.common.DuplicateException;
import org.example.ruiji.common.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> capture(SQLIntegrityConstraintViolationException exception){
        log.info(exception.getMessage());
        if (exception.getMessage().contains("Duplicate entry ")){
            String[] s = exception.getMessage().split(" ");
            String name = s[2];
            return R.error(name + " 此账户名重复");

        }
        return R.error("未知错误");
    }

    /**
     * 解决菜单分类和菜品或者套餐关联时抛出异常后，进行全局处理的方法
     * @param exception
     * @return
     */
    @ExceptionHandler(DuplicateException.class)
    public R<String> capture(DuplicateException exception){
        log.error(exception.getMessage());

        return R.error(exception.getMessage());
    }
}
