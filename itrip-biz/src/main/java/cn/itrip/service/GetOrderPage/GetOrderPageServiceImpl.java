package cn.itrip.service.GetOrderPage;

import cn.itrip.beans.pojo.ItripHotelOrder;
import cn.itrip.common.Page;
import cn.itrip.dao.hotelorder.ItripHotelOrderMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class GetOrderPageServiceImpl implements GetOrderPageService {
    @Resource
    private ItripHotelOrderMapper orderMapper;

    @Override
    public Page<ItripHotelOrder> getItripHotelOrderListByMap(Map map) throws Exception {
        Integer beginPos = ((Integer) map.get("pageNo") - 1) * (Integer) map.get("pageSize");
        List list = orderMapper.getItripHotelOrderListByMap(map);
        Integer total = orderMapper.getOrderCountByMap(map);
        Page page = new Page((Integer) map.get("pageNo"), (Integer) map.get("pageSize"), total);
        page.setRows(list);


        return page;
    }
}
