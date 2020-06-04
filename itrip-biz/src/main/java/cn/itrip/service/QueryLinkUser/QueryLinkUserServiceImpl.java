package cn.itrip.service.QueryLinkUser;

import cn.itrip.beans.pojo.ItripUserLinkUser;
import cn.itrip.dao.userlinkuser.ItripUserLinkUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class QueryLinkUserServiceImpl implements QueryLinkUserService {
    @Resource
    private ItripUserLinkUserMapper linkUserMapper;

    @Override
    public List<ItripUserLinkUser> queryLinkUser(Map map) throws Exception {
        return linkUserMapper.getItripUserLinkUserListByMap(map);
    }
}
