package cn.itrip.service.PreOrderInfo;

import cn.itrip.beans.pojo.ItripHotel;
import cn.itrip.beans.pojo.ItripHotelOrder;
import cn.itrip.beans.pojo.ItripHotelRoom;
import cn.itrip.beans.vo.store.StoreVO;

import java.util.List;
import java.util.Map;

public interface PreOrderInfoService {
    ItripHotel queryHotelById(Long id) throws Exception;

    boolean validateroomstore(Map map) throws Exception;

    ItripHotelRoom queryRoomById(Long id) throws Exception;

    List<StoreVO> queryRoomStore(Map map) throws Exception;

    Map insertOrder(ItripHotelOrder order) throws Exception;

    ItripHotelOrder getItripHotelOrderById(Long id) throws Exception;

    int modifyOrder(ItripHotelOrder order) throws Exception;

    boolean flushOrderStatus(Integer type) throws Exception;
}
