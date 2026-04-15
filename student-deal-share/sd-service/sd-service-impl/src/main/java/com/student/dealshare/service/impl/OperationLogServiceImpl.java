package com.student.dealshare.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.dealshare.mapper.OperationLogMapper;
import com.student.dealshare.model.entity.OperationLog;
import com.student.dealshare.service.api.OperationLogService;
import org.springframework.stereotype.Service;

/**
 * 操作日志服务实现类
 */
@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog> implements OperationLogService {
}
