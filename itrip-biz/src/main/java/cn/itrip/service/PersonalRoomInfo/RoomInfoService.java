package cn.itrip.service.PersonalRoomInfo;

import cn.itrip.beans.vo.order.ItripPersonalOrderRoomVO;

public interface RoomInfoService {
    ItripPersonalOrderRoomVO getOrderRoomInfo(Long id) throws Exception;
}
