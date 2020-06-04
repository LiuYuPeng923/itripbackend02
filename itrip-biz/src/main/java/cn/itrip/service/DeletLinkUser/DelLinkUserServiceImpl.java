package cn.itrip.service.DeletLinkUser;

import cn.itrip.dao.orderlinkuser.ItripOrderLinkUserMapper;
import cn.itrip.dao.userlinkuser.ItripUserLinkUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.util.List;

@Service
public class DelLinkUserServiceImpl implements DelLinkUserService {
    @Resource
    private ItripOrderLinkUserMapper itripOrderLinkUserMapper;
    @Resource
    private ItripUserLinkUserMapper userLinkUserMapper;

    @Override
    public List<Long> queryItripOrderByOrder() throws Exception {
        return null;
    }

    @Override
    public Integer deleteItripUser(Array ids) throws Exception {
        return null;
    }
}
