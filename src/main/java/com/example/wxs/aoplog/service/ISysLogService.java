package com.example.wxs.aoplog.service;

import com.example.wxs.aoplog.entity.SysLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cmy
 * @since 2020-03-04
 */

public interface ISysLogService extends IService<SysLog> {
    /**
     * 插入日志
     * @param entity
     * @return
     */
    int insertLog(SysLog entity);
}
