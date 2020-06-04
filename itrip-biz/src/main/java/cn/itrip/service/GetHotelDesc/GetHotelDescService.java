package cn.itrip.service.GetHotelDesc;

import cn.itrip.beans.pojo.ItripHotel;

public interface GetHotelDescService {
    ItripHotel queryItripHotelById(Long id) throws Exception;
}
