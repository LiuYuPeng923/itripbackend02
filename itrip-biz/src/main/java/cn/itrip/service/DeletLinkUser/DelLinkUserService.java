package cn.itrip.service.DeletLinkUser;

import java.lang.reflect.Array;
import java.util.List;

public interface DelLinkUserService {
    List<Long> queryItripOrderByOrder() throws Exception;

    Integer deleteItripUser(Array ids) throws Exception;


}
