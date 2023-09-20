package org.example.ruiji.exception;

import lombok.extern.slf4j.Slf4j;
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
}
