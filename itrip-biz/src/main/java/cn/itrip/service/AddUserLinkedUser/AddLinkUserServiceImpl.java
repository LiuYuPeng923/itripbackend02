package cn.itrip.service.AddUserLinkedUser;

import cn.itrip.beans.pojo.ItripUserLinkUser;
import cn.itrip.dao.userlinkuser.ItripUserLinkUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AddLinkUserServiceImpl implements AddLinkUserService {
    @Resource
    private ItripUserLinkUserMapper itripUserLinkUserMapper;

    @Override
    public Integer addLinkUse(ItripUserLinkUser itripUserLinkUser) throws Exception {
        return itripUserLinkUserMapper.insertItripUserLinkUser(itripUserLinkUser);
    }
}
