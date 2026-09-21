package com.example.demo.controller.advice;

import com.example.demo.controller.dto.LoginResponseDto;
import com.example.demo.controller.advice.dto.InvalidParameterDto;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorType;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(CustomException.class)
    public LoginResponseDto<Void> handle(CustomException exception) {
        ErrorType type = exception.getType();
        log.makeLoggingEventBuilder(type.getLevel())
                .log(exception.getMessage(), exception);
        return LoginResponseDto.failed(type);
    }

    //  (1-1) @Controller 내 메서드에 @Min 그대로 적용 (알아서 처리해줌 - 간단한 객체에 대해)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HandlerMethodValidationException.class)
    public LoginResponseDto<List<InvalidParameterDto>> handle(HandlerMethodValidationException exception) {
        List<InvalidParameterDto> parameterInvalidDetails = new ArrayList<>();
        for (ParameterValidationResult eachParameter : exception.getValueResults()) {
            InvalidParameterDto.InvalidParameterDtoBuilder eachParameterInvalidDetailBuilder = InvalidParameterDto.builder();
            eachParameterInvalidDetailBuilder.parameter(eachParameter.getMethodParameter().getParameterName());
            eachParameterInvalidDetailBuilder.actualValue(eachParameter.getArgument());
            List<MessageSourceResolvable> validations = eachParameter.getResolvableErrors();
            for (MessageSourceResolvable eachValidation : validations) {
                eachParameterInvalidDetailBuilder.criteria(eachValidation.getCodes()[eachValidation.getCodes().length - 1]);
                eachParameterInvalidDetailBuilder.criteriaValue(eachValidation.getArguments()[eachValidation.getArguments().length - 1]);
                String violationMessage = eachValidation.getDefaultMessage();
                eachParameterInvalidDetailBuilder.violationMessage(violationMessage);
            }
            InvalidParameterDto eachParameterInvalidDetail = eachParameterInvalidDetailBuilder.build();
            parameterInvalidDetails.add(eachParameterInvalidDetail);
        }
        log.warn("@PathVariable, @RequestParam 으로 받는 요청 간단한 객체(Integer 등) 내 검증 실패 값이 존재 : {}", parameterInvalidDetails, exception);
        return LoginResponseDto.failed("@PathVariable, @RequestParam 으로 받는 요청 간단한 객체(Integer 등) 내 검증 실패 값이 존재",parameterInvalidDetails);
    }

    //  (1-2) @Controller 내 메서드에 @Valid + DTO 객체 내 @Min
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public LoginResponseDto<List<InvalidParameterDto>> handle(MethodArgumentNotValidException exception) {
        List<InvalidParameterDto> parameterInvalidDetails = new ArrayList<>();
        for (FieldError eachParameterValidation : exception.getBindingResult().getFieldErrors()) {
            InvalidParameterDto eachParameterInvalidDetail = InvalidParameterDto.builder()
                    .parameter(eachParameterValidation.getField())
                    .actualValue(eachParameterValidation.getRejectedValue())
                    .criteriaValue(eachParameterValidation.getArguments()[1])
                    .criteria(eachParameterValidation.getCode())
                    .violationMessage(eachParameterValidation.getDefaultMessage())
                    .build();
            parameterInvalidDetails.add(eachParameterInvalidDetail);
        }
        log.warn("@RequestBody, @ModelAttribute 으로 받는 요청 DTO 객체 내 검증 실패 값이 존재 : {}", parameterInvalidDetails, exception);
        return LoginResponseDto.failed("@RequestBody, @ModelAttribute 으로 받는 요청 DTO 객체 내 검증 실패 값이 존재", parameterInvalidDetails);
    }

    //  (2) 아무 클래스에 @Validated + 아무 메서드 내 파라미터에 @Min
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public LoginResponseDto<List<InvalidParameterDto>> handle(ConstraintViolationException exception) {
        List<InvalidParameterDto> parameterInvalidDetails = new ArrayList<>();
        for (ConstraintViolation eachParameterValidation : exception.getConstraintViolations()) {
            InvalidParameterDto eachParameterInvalidDetail = InvalidParameterDto.builder()
                    .parameter(eachParameterValidation.getPropertyPath().toString())
                    .actualValue(eachParameterValidation.getInvalidValue())
//                  .criteriaValue()
//                  .criteria()
                    .violationMessage(eachParameterValidation.getMessage())
                    .build();
            parameterInvalidDetails.add(eachParameterInvalidDetail);
        }
        log.warn("@Service, @Repository 등의 기타 클래스 내 메서드 파라미터들에 대한 검증 실패 값이 존재 : {}", parameterInvalidDetails, exception);
        return LoginResponseDto.failed("@Service, @Repository 등의 기타 클래스 내 메서드 파라미터들에 대한 검증 실패 값이 존재", parameterInvalidDetails);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public LoginResponseDto<Void> handle(Exception exception) {
        log.error(exception.getMessage(), exception);
        return LoginResponseDto.failed(exception.getMessage());
    }
}
