package cn.itrip.service.SuccessDerInfo;

import cn.itrip.beans.pojo.ItripHotelOrder;
import cn.itrip.beans.pojo.ItripHotelRoom;
import cn.itrip.dao.hotelorder.ItripHotelOrderMapper;
import cn.itrip.dao.hotelroom.ItripHotelRoomMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class QuerySuccessDerInfoSeviceImpl implements QuerySuccessDerInfoService {
    @Resource
    private ItripHotelOrderMapper orderMapper;

    @Override
    public ItripHotelOrder queryHotelSuccessOrderById(Long id) throws Exception {
        return orderMapper.getItripHotelOrderById(id);
    }

    @Resource
    private ItripHotelRoomMapper roomMapper;

    @Override
    public ItripHotelRoom getItripHotelRoomById(Long id) throws Exception {
        return roomMapper.getItripHotelRoomById(id);
    }

}
