package cn.itrip.service.HotelDetails;

import cn.itrip.beans.pojo.ItripLabelDic;

import java.util.List;

public interface HotelDetailsService {
    List<ItripLabelDic> getHotleDetailByHotelId(Long id) throws Exception;
}
