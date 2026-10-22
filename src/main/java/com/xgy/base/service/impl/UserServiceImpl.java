package com.xgy.base.service.impl;

import com.xgy.base.entity.User;
import com.xgy.base.mapper.UserMapper;
import com.xgy.base.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xgy
 * @since 2026-10-22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
