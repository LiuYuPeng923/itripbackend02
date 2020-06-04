package cn.itrip.service.GetHotelDesc;

import cn.itrip.beans.pojo.ItripHotel;
import cn.itrip.dao.hotel.ItripHotelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class GetHotelDescServiceImpl implements GetHotelDescService {
    @Resource
    private ItripHotelMapper hotelMapper;

    @Override
    public ItripHotel queryItripHotelById(Long id) throws Exception {
        return hotelMapper.getItripHotelById(id);
    }
}
