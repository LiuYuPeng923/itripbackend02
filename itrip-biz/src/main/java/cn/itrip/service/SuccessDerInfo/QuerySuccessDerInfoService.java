package cn.itrip.service.SuccessDerInfo;

import cn.itrip.beans.pojo.ItripHotelOrder;
import cn.itrip.beans.pojo.ItripHotelRoom;

public interface QuerySuccessDerInfoService {
    ItripHotelOrder queryHotelSuccessOrderById(Long id) throws Exception;

    ItripHotelRoom getItripHotelRoomById(Long id) throws Exception;

}
