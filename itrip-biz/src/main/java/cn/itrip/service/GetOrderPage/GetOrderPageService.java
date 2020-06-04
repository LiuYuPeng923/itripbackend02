package cn.itrip.service.GetOrderPage;

import cn.itrip.beans.pojo.ItripHotelOrder;
import cn.itrip.common.Page;

import java.util.List;
import java.util.Map;

public interface GetOrderPageService {
    Page<ItripHotelOrder> getItripHotelOrderListByMap(Map map) throws Exception;
}
