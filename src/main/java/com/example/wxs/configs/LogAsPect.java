package com.example.wxs.configs;

import com.example.wxs.aoplog.entity.SysLog;
import com.example.wxs.aoplog.service.impl.SysLogServiceImpl;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

/**
 * @author cmy 2020.03.04
 */
@Aspect
@Component
public class LogAsPect {

    private final static Logger log=org.slf4j.LoggerFactory.getLogger(LogAsPect.class);

    @Autowired
    private SysLogServiceImpl sysLogService;

    @Pointcut("@annotation(com.example.wxs.configs.Log)")
    public void pointcut() {}

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point){
        Object result=null;
        long beginTime = System.currentTimeMillis();
        try{
            log.info("我在目标方法之前执行！");
            //启动目标方法执行
            result=point.proceed();
            long endTime = System.currentTimeMillis();
            insertLog(point,endTime-beginTime);
        }catch (Throwable e){

        }
        return result;
    }

    private void insertLog(ProceedingJoinPoint point,long time){
        MethodSignature signature=(MethodSignature)point.getSignature();
        Method method=signature.getMethod();
        SysLog sys_log=new SysLog();
        Log userAction =method.getAnnotation(Log.class);
        if(userAction!=null){
            sys_log.setUserAction(userAction.value());
        }
        String className=point.getTarget().getClass().getName();
        String methodName=signature.getName();
        String args= Arrays.toString(point.getArgs());

        int userid=1;
        sys_log.setUserId(userid);
        sys_log.setCreateTime(new java.sql.Timestamp(new Date().getTime()));
        log.info("登陆人：{},类名:{},方法名:{},参数：{},执行时间：{}",userid, className, methodName, args, time);
        sysLogService.insertLog(sys_log);

    }


    @Pointcut("execution(public * com.example.wxs.controller..*.*(..))")
    public void pointcutController(){

    }

    @Before("pointcutController()")
    public void around2(JoinPoint point){
        String methodNam = point.getSignature().getDeclaringTypeName() + "." + point.getSignature().getName();
        String params = Arrays.toString(point.getArgs());
        log.info("get in {} params :{}",methodNam,params);
    }
}