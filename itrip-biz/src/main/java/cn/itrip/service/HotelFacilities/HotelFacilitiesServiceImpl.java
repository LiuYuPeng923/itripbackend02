package cn.itrip.service.HotelFacilities;

import cn.itrip.beans.pojo.ItripHotel;
import cn.itrip.beans.vo.hotel.ItripSearchFacilitiesHotelVO;
import cn.itrip.dao.hotel.ItripHotelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class HotelFacilitiesServiceImpl implements HotelFacilitiesService {
    @Resource
    private ItripHotelMapper hotelMapper;

    @Override
    public ItripSearchFacilitiesHotelVO queryHotelFacilites(Long id) throws Exception {
        return hotelMapper.getItripHotelFacilitiesById(id);
    }
}
