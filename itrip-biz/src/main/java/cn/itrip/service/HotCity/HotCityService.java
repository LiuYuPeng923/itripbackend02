package cn.itrip.service.HotCity;

import cn.itrip.beans.pojo.ItripAreaDic;
import cn.itrip.beans.pojo.ItripHotel;

import java.util.List;
import java.util.Map;

public interface HotCityService {

    List<ItripAreaDic> getHotelListByMap(Map map) throws Exception;
}
