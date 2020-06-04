package cn.itrip.service.HotelRoomByHotel;

import cn.itrip.beans.vo.hotelroom.ItripHotelRoomVO;

import java.util.List;
import java.util.Map;

public interface HotelRoomByHotelService {

    List<ItripHotelRoomVO> queryhotelroombyhotel(Map map) throws Exception;
}
