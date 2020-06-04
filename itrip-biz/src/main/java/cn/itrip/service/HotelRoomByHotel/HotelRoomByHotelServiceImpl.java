package cn.itrip.service.HotelRoomByHotel;

import cn.itrip.beans.vo.hotelroom.ItripHotelRoomVO;
import cn.itrip.dao.hotelroom.ItripHotelRoomMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class HotelRoomByHotelServiceImpl implements HotelRoomByHotelService {
    @Resource
    private ItripHotelRoomMapper hotelRoomMapper;

    @Override
    public List<ItripHotelRoomVO> queryhotelroombyhotel(Map map) throws Exception {
        return hotelRoomMapper.getItripHotelRoomListByMap(map);
    }
}
