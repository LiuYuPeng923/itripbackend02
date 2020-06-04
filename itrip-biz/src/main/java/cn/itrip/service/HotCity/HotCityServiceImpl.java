package cn.itrip.service.HotCity;

import cn.itrip.beans.pojo.ItripAreaDic;
import cn.itrip.beans.pojo.ItripHotel;
import cn.itrip.dao.areadic.ItripAreaDicMapper;
import cn.itrip.dao.hotel.ItripHotelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class HotCityServiceImpl implements HotCityService {
    @Resource
    private ItripAreaDicMapper dicMapper;

    @Override
    public List<ItripAreaDic> getHotelListByMap(Map map) throws Exception {
        return dicMapper.getItripAreaDicListByMap(map);
    }
}
