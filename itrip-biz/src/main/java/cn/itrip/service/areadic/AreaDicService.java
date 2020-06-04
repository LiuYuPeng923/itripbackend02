package cn.itrip.service.areadic;

import cn.itrip.beans.pojo.ItripAreaDic;

import java.util.List;
import java.util.Map;

public interface AreaDicService {

    List<ItripAreaDic> getAreaDicList(Map map) throws Exception;

}
