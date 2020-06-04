package cn.itrip.service.QueryLinkUser;

import cn.itrip.beans.pojo.ItripUserLinkUser;

import java.util.List;
import java.util.Map;

public interface QueryLinkUserService {
    List<ItripUserLinkUser> queryLinkUser(Map map) throws Exception;
}
