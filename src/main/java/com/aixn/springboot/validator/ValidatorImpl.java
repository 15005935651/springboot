package com.aixn.springboot.validator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @author zxx
 * @date 2018/12/15 20:35
 */
@Component
public class ValidatorImpl implements InitializingBean {

    private Validator validator;

    //实现校验方法并返回校验结果
    public ValidationResult validate(Object bean) {
        final ValidationResult result = new ValidationResult();
        Set<ConstraintViolation<Object>> constraintViolationSet = validator.validate(bean);
        if (constraintViolationSet.size() > 0) {
            //有异常
            result.setHasErrors(true);
            constraintViolationSet.forEach(constraintViolation -> {
                String errMsg = constraintViolation.getMessage();
                String propertyName = constraintViolation.getPropertyPath().toString();
                result.getErrMsgMap().put(propertyName,errMsg);
            });
        }
        return result;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //将hibernate validation同时工厂模式进行实例化
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
}
