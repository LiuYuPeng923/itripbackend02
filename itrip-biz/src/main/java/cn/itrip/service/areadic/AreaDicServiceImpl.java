package cn.itrip.service.areadic;

import cn.itrip.beans.pojo.ItripAreaDic;
import cn.itrip.dao.areadic.ItripAreaDicMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class AreaDicServiceImpl implements AreaDicService {
    @Resource
    private ItripAreaDicMapper dicMapper;

    @Override
    public List<ItripAreaDic> getAreaDicList(Map map) throws Exception {

        return dicMapper.getItripAreaDicListByMap(map);
    }
}
