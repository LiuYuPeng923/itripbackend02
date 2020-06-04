package cn.itrip.service.PersonalRoomInfo;

import cn.itrip.beans.vo.order.ItripPersonalOrderRoomVO;
import cn.itrip.dao.hotelorder.ItripHotelOrderMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RoomInfoServiceImpl implements RoomInfoService {
    @Resource
    private ItripHotelOrderMapper orderMapper;

    @Override
    public ItripPersonalOrderRoomVO getOrderRoomInfo(Long id) throws Exception {
        return orderMapper.getItripHotelOrderRoomInfoById(id);
    }
}
