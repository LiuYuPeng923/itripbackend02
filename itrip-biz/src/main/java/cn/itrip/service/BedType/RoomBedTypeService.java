package cn.itrip.service.BedType;

import cn.itrip.beans.pojo.ItripLabelDic;

import java.util.List;
import java.util.Map;

public interface RoomBedTypeService {
    List<ItripLabelDic> queryRoomBedType(Map map) throws Exception;

}
