package cn.itrip.service.ModifyLinkUser;

import cn.itrip.beans.pojo.ItripUserLinkUser;
import cn.itrip.dao.userlinkuser.ItripUserLinkUserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class ModifyLinkUserServiceImpl implements ModifyLinkUserService {
    @Resource
    private ItripUserLinkUserMapper linkUserMapper;

    @Transactional
    @Override
    public Integer modifyLinkUser(ItripUserLinkUser itripUserLinkUser) throws Exception {
        return linkUserMapper.updateItripUserLinkUser(itripUserLinkUser);
    }
}
