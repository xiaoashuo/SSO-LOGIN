package com.lovecyy.sso.common.service.impl;

import com.lovecyy.sso.common.dto.HpUser;
import com.lovecyy.sso.common.mapper.HpUserMapper;
import com.lovecyy.sso.common.service.HpUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @topic 
*
* @author ys
* @date 2019/9/10 11:42
*/
@Service
public class HpUserServiceImpl implements HpUserService{

    @Autowired
    private HpUserMapper hpUserMapper;

    @Override
    public List<HpUser> selectAll() {
        return hpUserMapper.selectAll();
    }
}
