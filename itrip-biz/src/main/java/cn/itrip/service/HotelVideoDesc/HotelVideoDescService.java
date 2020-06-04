package cn.itrip.service.HotelVideoDesc;

import cn.itrip.beans.pojo.ItripHotelFeature;
import cn.itrip.beans.vo.hotel.HotelVideoDescVO;

import java.util.List;
import java.util.Map;

public interface HotelVideoDescService {
    HotelVideoDescVO getHotelVideoDesc(Long id) throws Exception;
}
