package cn.itrip.service.PersonalOrder;

import cn.itrip.beans.pojo.ItripHotelOrder;

public interface GetPersonalOrderService {
    ItripHotelOrder getHotelOredById(Long id) throws Exception;
}
