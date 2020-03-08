package com.example.wxs.aoplog.service.impl;

import com.example.wxs.aoplog.entity.SysLog;
import com.example.wxs.aoplog.mapper.SysLogMapper;
import com.example.wxs.aoplog.service.ISysLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cmy
 * @since 2020-03-04
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements ISysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public int insertLog(SysLog entity) {
        return sysLogMapper.insert(entity);
    }
}
