package cn.itrip.controller;

import cn.itrip.beans.dto.Dto;
import cn.itrip.beans.pojo.ItripHotelOrder;
import cn.itrip.beans.pojo.ItripUser;
import cn.itrip.beans.vo.order.ItripModifyHotelOrderVO;
import cn.itrip.beans.vo.order.ItripPersonalHotelOrderVO;
import cn.itrip.beans.vo.order.ItripPersonalOrderRoomVO;
import cn.itrip.beans.vo.order.ValidateRoomStoreVO;
import cn.itrip.common.DtoUtil;
import cn.itrip.common.EmptyUtils;
import cn.itrip.common.ValidationToken;
import cn.itrip.service.PersonalOrder.GetPersonalOrderService;
import cn.itrip.service.PersonalRoomInfo.RoomInfoService;
import cn.itrip.service.order.OrderService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/hotelorder")
public class OrderController {
    @Resource
    private ValidationToken validationToken;
    @Resource
    private OrderService orderService;

    @RequestMapping(value = "/validateroomstore", method = RequestMethod.POST)
    @ResponseBody
    public Dto validateRoomStore(@RequestBody ValidateRoomStoreVO vo, HttpServletRequest request) {
        String token = request.getHeader("token");
        ItripUser user = validationToken.getCurrentUser(token);
        if (user == null) {
            return DtoUtil.returnFail("token失效，请重登录", "100000");
        }
        if (EmptyUtils.isEmpty(vo.getHotelId())) {
            return DtoUtil.returnFail(" hotelId不能为空", "100515");
        }
        if (EmptyUtils.isEmpty(vo.getRoomId())) {
            return DtoUtil.returnFail("roomId不能为空", "100516");
        }
        Map map = new HashMap();
        map.put("hotelId", vo.getHotelId());
        map.put("roomId", vo.getRoomId());
        map.put("startTime", vo.getCheckInDate());
        map.put("endTime", vo.getCheckOutDate());
        map.put("count", vo.getCount());
        try {
            boolean flag = orderService.validationRoomStore(map);
            map.clear();
            map.put("flag", flag);
            return DtoUtil.returnSuccess("成功", map);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常", "100517");
        }


    }

    @Resource
    private GetPersonalOrderService personalOrderService;

    @RequestMapping(value = "/getpersonalorderinfo/{orderId}", method = RequestMethod.GET)
    @ResponseBody
    public Dto getPersonalOrderInfo(@PathVariable String orderId, HttpServletRequest request) {
        String token = request.getHeader("token");
        ItripUser user = validationToken.getCurrentUser(token);
        if (user == null) {
            return DtoUtil.returnFail("token失效，请重登录", "100000");
        }
        if (EmptyUtils.isEmpty(orderId)) {
            return DtoUtil.returnFail("请传递参数：orderId", "100525 ");
        }

        try {
            ItripHotelOrder hotelOredById = personalOrderService.getHotelOredById(Long.parseLong(orderId));
            if (hotelOredById == null) {
                return DtoUtil.returnFail(" 没有相关订单信息", "100526");
            }
           /* 订单状态(0：待支付 2:支付成功 3:已消费 4:已点评)的流程：{"1":"订单提交","2":"订单支付","3":"支付成功","4":"入住","5":"订单点评","6":"订单完成"}
            订单状态(1:已取消)的流程：{"1":"订单提交","2":"订单支付","3":"订单取消"}
            支持支付类型(roomPayType)：{"1":"在线付","2":"线下付","3":"不限"}*/
            ItripPersonalHotelOrderVO vo = new ItripPersonalHotelOrderVO();
            BeanUtils.copyProperties(hotelOredById, vo);
            Object ok = JSON.parse("{\"1\":\"提交订单\",\"2\":订单支付\",\"3\":支付成功\",\"4\":入住\",\"5\":订单点评\",\"6\":订单完成\"}");
            Object cancel = JSON.parse("{\"1\":\"提交订单\",\"2\":订单支付\",\"3\":订单取消\"");
            if (vo.getOrderStatus() == 0) {
                vo.setOrderProcess(orderId);
                vo.setProcessNode("2");
            } else if (vo.getOrderStatus() == 1) {
                vo.setOrderProcess(cancel);
                vo.setProcessNode("3");
            } else if (vo.getOrderStatus() == 2) {
                vo.setOrderProcess(ok);
                vo.setProcessNode("3");
            } else if (vo.getOrderStatus() == 3) {
                vo.setOrderProcess(ok);
                vo.setProcessNode("4");
            } else if (vo.getOrderStatus() == 4) {
                vo.setOrderProcess(ok);
                vo.setProcessNode("5");
            } else if (vo.getOrderStatus() == 5) {
                vo.setOrderProcess(ok);
                vo.setProcessNode("6");
            } else {
                vo.setOrderProcess(null);
                vo.setProcessNode(null);
            }
            vo.setPayType(hotelOredById.getPayType());
            return DtoUtil.returnDataSuccess(vo);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail(" 获取个人订单信息错误", "100527 ");
        }

    }

    @Resource
    private RoomInfoService infoService;

    @RequestMapping(value = "/getpersonalorderroominfo/{orderId}", method = RequestMethod.GET)
    @ResponseBody
    public Dto getPersonalOrderRoomInfo(@PathVariable String orderId, HttpServletRequest request) {
        String token = request.getHeader("token");
        ItripUser user = validationToken.getCurrentUser(token);
        if (user == null) {
            return DtoUtil.returnFail("token失效，请重登录", "100000");
        }
        if (orderId == null) {
            return DtoUtil.returnFail("请传递参数：orderId", "100529");
        }

        try {
            ItripPersonalOrderRoomVO orderRoomInfo = infoService.getOrderRoomInfo(Long.parseLong(orderId));
            if (orderRoomInfo == null) {
                return DtoUtil.returnFail("没有相关订单房型信息", "100530");
            }
            return DtoUtil.returnDataSuccess(orderRoomInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail(" 获取个人订单房型信息错误", "100531");
        }

    }


}
