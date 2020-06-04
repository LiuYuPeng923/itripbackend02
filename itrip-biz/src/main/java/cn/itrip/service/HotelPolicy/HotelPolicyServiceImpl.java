package cn.itrip.service.HotelPolicy;

import cn.itrip.beans.vo.hotel.ItripSearchPolicyHotelVO;
import cn.itrip.dao.hotel.ItripHotelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class HotelPolicyServiceImpl implements HotelPolicyService {
    @Resource
    private ItripHotelMapper hotelMapper;

    @Override
    public ItripSearchPolicyHotelVO queryHotelPolicy(Long id) throws Exception {
        return hotelMapper.queryHotelPolicy(id);
    }
}
