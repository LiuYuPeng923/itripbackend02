package cn.itrip.service.TravelType;

import cn.itrip.beans.pojo.ItripLabelDic;

import java.util.List;
import java.util.Map;

public interface TravelTypeService {

    List<ItripLabelDic> travelType(Map map) throws Exception;

}
