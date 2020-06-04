package cn.itrip.service.PersonalOrder;

import cn.itrip.beans.pojo.ItripHotelOrder;
import cn.itrip.dao.hotelorder.ItripHotelOrderMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class GetPersonalOrderServiceIpl implements GetPersonalOrderService {
    @Resource
    private ItripHotelOrderMapper itripHotelOrderMapper;

    @Override
    public ItripHotelOrder getHotelOredById(Long id) throws Exception {
        return itripHotelOrderMapper.getItripHotelOrderById(id);
    }
}
