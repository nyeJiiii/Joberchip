package kr.joberchip.server.v1._errors;

import kr.joberchip.server.v1._errors.exceptions.ApiClientException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Aspect
@Component
public class GlobalValidationHandler {

  @Pointcut(value = "@annotation(org.springframework.web.bind.annotation.PostMapping)")
  public void postMapping() {}

  @Pointcut(value = "@annotation(org.springframework.web.bind.annotation.PutMapping)")
  public void putMapping() {}

  @Before("postMapping() || putMapping()")
  public void validationAdvice(JoinPoint jp) {
    Object[] args = jp.getArgs();
    for (Object arg : args) {
      if (arg instanceof Errors errors) {
        if (errors.hasErrors()) {
          throw new ApiClientException(
              errors.getFieldErrors().get(0).getField(),
              errors.getFieldErrors().get(0).getDefaultMessage());
        }
      }
    }
  }
}
