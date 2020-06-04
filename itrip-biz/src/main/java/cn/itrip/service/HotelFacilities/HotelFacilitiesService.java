package cn.itrip.service.HotelFacilities;

import cn.itrip.beans.pojo.ItripHotel;
import cn.itrip.beans.vo.hotel.ItripSearchFacilitiesHotelVO;

public interface HotelFacilitiesService {

    ItripSearchFacilitiesHotelVO queryHotelFacilites(Long id) throws Exception;
}
