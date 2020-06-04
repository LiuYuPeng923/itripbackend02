package cn.itrip.service.HotelFeature;

import cn.itrip.beans.pojo.ItripHotelFeature;
import cn.itrip.beans.pojo.ItripLabelDic;

import java.util.List;
import java.util.Map;

public interface HotelFeatureService {

    List<ItripLabelDic> getHotelFeature(Map map) throws Exception;


}
