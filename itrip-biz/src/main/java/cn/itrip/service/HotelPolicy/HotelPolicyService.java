package cn.itrip.service.HotelPolicy;

import cn.itrip.beans.vo.hotel.ItripSearchPolicyHotelVO;

public interface HotelPolicyService {
    ItripSearchPolicyHotelVO queryHotelPolicy(Long id) throws Exception;
}
