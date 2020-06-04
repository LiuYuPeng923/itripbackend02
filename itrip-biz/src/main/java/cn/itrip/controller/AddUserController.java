package cn.itrip.controller;

import cn.itrip.beans.dto.Dto;
import cn.itrip.beans.pojo.*;
import cn.itrip.beans.vo.order.*;
import cn.itrip.beans.vo.store.StoreVO;
import cn.itrip.beans.vo.userinfo.ItripAddUserLinkUserVO;
import cn.itrip.beans.vo.userinfo.ItripModifyUserLinkUserVO;
import cn.itrip.beans.vo.userinfo.ItripSearchUserLinkUserVO;
import cn.itrip.common.*;
import cn.itrip.service.AddUserLinkedUser.AddLinkUserService;
import cn.itrip.service.GetOrderPage.GetOrderPageService;
import cn.itrip.service.ModifyLinkUser.ModifyLinkUserService;
import cn.itrip.service.PreOrderInfo.PreOrderInfoService;
import cn.itrip.service.QueryLinkUser.QueryLinkUserService;
import cn.itrip.service.SuccessDerInfo.QuerySuccessDerInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/api/userinfo")
public class AddUserController {
    @Resource
    private ValidationToken validationToken;
    @Resource
    private AddLinkUserService userService;

    @RequestMapping(value = "/adduserlinkuser")
    @ResponseBody
    public Dto addUserLinkUser(ItripAddUserLinkUserVO vo, HttpServletRequest request) {
        String token = request.getHeader("token");
        String userAgent = request.getHeader("user-agent");

        try {
            if (!validationToken.validate(userAgent, token)) {
                return DtoUtil.returnFail("token失效，请重登录", "100000");
            }
            if (EmptyUtils.isEmpty(vo)) {
                return DtoUtil.returnFail("不能提交空，请填写常用联系人信息", "100412");
            }
            ItripUserLinkUser linkUser = new ItripUserLinkUser();
            ItripUser currentUser = validationToken.getCurrentUser(token);

            BeanUtils.copyProperties(vo, linkUser);
            linkUser.setCreationDate(new Date());
            linkUser.setCreatedBy(currentUser.getId());
            linkUser.setModifyDate(new Date());
            userService.addLinkUse(linkUser);
            return DtoUtil.returnSuccess("新增联系人成功");
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("新增常用联系人失败", "100411");
        }


    }

    @Resource
    private QueryLinkUserService linkUserService;

    @RequestMapping(value = "/queryuserlinkuser", method = RequestMethod.POST)
    @ResponseBody
    public Dto queryUserLinkUser(ItripSearchUserLinkUserVO itripSearchUserLinkUserVO, HttpServletRequest request) {
        String token = request.getHeader("token");
        String userAgent = request.getHeader("user-agent");

        try {
            if (!validationToken.validate(userAgent, token)) {
                return DtoUtil.returnFail("token失效，请重登录", "100000");
            }
            Map map = new HashMap();
            map.put("linkUserName", itripSearchUserLinkUserVO.getLinkUserName());
            List<ItripUserLinkUser> itripUserLinkUsers = linkUserService.queryLinkUser(map);
            return DtoUtil.returnDataSuccess(itripUserLinkUsers);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("获取常用联系人信息失败", "100401");
        }
    }

    @Resource
    private ModifyLinkUserService modifyLinkUserService;

    @RequestMapping(value = "/modifyuserlinkuser", method = RequestMethod.POST)
    @ResponseBody
    public Dto modifyUserLinkUser(ItripModifyUserLinkUserVO itripModifyUserLinkUserVO, HttpServletRequest request) {
        String token = request.getHeader("token");
        String userAgent = request.getHeader("user-agent");

        try {
            if (!validationToken.validate(userAgent, token)) {
                return DtoUtil.returnFail("token失效，请重登录", "100000");
            }
            if (EmptyUtils.isEmpty(itripModifyUserLinkUserVO)) {
                return DtoUtil.returnFail("不能提交空，请填写常用联系人信息", "100422");
            }
            ItripUserLinkUser linkUser = new ItripUserLinkUser();
            BeanUtils.copyProperties(itripModifyUserLinkUserVO, linkUser);
            ItripUser currentUser = validationToken.getCurrentUser(token);
            linkUser.setModifyDate(new Date());
            linkUser.setModifiedBy(currentUser.getId());
            modifyLinkUserService.modifyLinkUser(linkUser);

            return DtoUtil.returnSuccess("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("修改常用联系人失败", "100421");
        }
    }

    @Resource
    private GetOrderPageService orderPageService;

    @RequestMapping(value = "/getpersonalorderlist", method = RequestMethod.POST)
    @ResponseBody
    public Dto getPersonalOrderList(@RequestBody ItripSearchOrderVO itripSearchOrderVO, HttpServletRequest request) {
        String token = request.getHeader("token");
        String userAgent = request.getHeader("user-agent");
        Map map = new HashMap();
        if (validationToken.getCurrentUser(token) == null) {
            return DtoUtil.returnFail("token失效，请重登录", "100000");
        }
        if (EmptyUtils.isEmpty(itripSearchOrderVO.getOrderType())) {
            return DtoUtil.returnFail("请传递参数：orderType", "100501 ");
        }
        if (EmptyUtils.isEmpty(itripSearchOrderVO.getOrderStatus())) {
            return DtoUtil.returnFail("请传递参数：orderStatus", "100502");
        }
        map.put("orederNo", itripSearchOrderVO.getOrderNo());
        map.put("linkUserName", itripSearchOrderVO.getLinkUserName());
        map.put("startDate", itripSearchOrderVO.getStartDate());
        map.put("endDate", itripSearchOrderVO.getEndDate());
        map.put("pageSize", itripSearchOrderVO.getPageSize() == null ? 1 : itripSearchOrderVO.getPageSize());
        map.put("pageNo", itripSearchOrderVO.getPageNo() == null ? 5 : itripSearchOrderVO.getPageNo());
        map.put("orderStatus", itripSearchOrderVO.getOrderStatus() == -1 ? null : itripSearchOrderVO.getOrderStatus());
        map.put("orderType", itripSearchOrderVO.getOrderType() == -1 ? null : itripSearchOrderVO.getOrderType());

        try {

            Page<ItripHotelOrder> itripHotelOrderListByMap = orderPageService.getItripHotelOrderListByMap(map);
            return DtoUtil.returnDataSuccess(itripHotelOrderListByMap);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail(" 获取个人订单列表错误", "100503");
        }

    }

    @Resource
    private QuerySuccessDerInfoService successDerInfoService;

    @RequestMapping(value = "/querysuccessorderinfo/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Dto querySuccessOrderInfo(@PathVariable Long id, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (validationToken.getCurrentUser(token) == null) {
            return DtoUtil.returnFail("token失效，请重登录", "100000");
        }
        if (id == null) {
            return DtoUtil.returnFail("id不能为空", "100519");
        }
        try {
            ItripHotelOrder itripHotelOrder = successDerInfoService.queryHotelSuccessOrderById(id);
            ItripHotelRoom itripHotelRoomById = successDerInfoService.getItripHotelRoomById(itripHotelOrder.getRoomId());
            Map map = new HashMap();
            map.put("id", itripHotelOrder.getId());
            map.put("orderNo", itripHotelOrder.getOrderNo());
            map.put("payType", itripHotelOrder.getPayType());
            map.put("payAmount", itripHotelOrder.getPayAmount());
            map.put("hotelName", itripHotelOrder.getHotelName());
            map.put("roomTitle", itripHotelRoomById.getRoomTitle());
            return DtoUtil.returnDataSuccess(map);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail(" 获取数据失败", "100520");
        }


    }

    @Resource
    private PreOrderInfoService infoService;

    @RequestMapping(value = "/getpreorderinfo", method = RequestMethod.POST)
    @ResponseBody
    public Dto getPreorderInfo(@RequestBody ValidateRoomStoreVO vo, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (validationToken.getCurrentUser(token) == null) {
            return DtoUtil.returnFail("token失效，请重登录", "100000");
        }
        if (EmptyUtils.isEmpty(vo.getHotelId())) {
            return DtoUtil.returnFail("hotelId不能为空", "100510");
        }
        if (EmptyUtils.isEmpty(vo.getRoomId())) {
            return DtoUtil.returnFail("roomId不能为空", "100511");
        }

        try {
            ItripHotel hotel = infoService.queryHotelById(vo.getHotelId());
            ItripHotelRoom room = infoService.queryRoomById(vo.getRoomId());
            Map map = new HashMap();
            map.put("hotelId", vo.getHotelId());
            map.put("roomId", vo.getRoomId());
            map.put("startTime", vo.getCheckInDate());
            map.put("endTime", vo.getCheckOutDate());
            boolean flag = infoService.validateroomstore(map);
            if (!flag) {
                return DtoUtil.returnFail(" 暂时无房", "100512");
            }
            List<StoreVO> list = infoService.queryRoomStore(map);
            PreAddOrderVO preAddOrderVO = new PreAddOrderVO();
            preAddOrderVO.setHotelId(vo.getHotelId());
            preAddOrderVO.setRoomId(vo.getRoomId());
            preAddOrderVO.setCheckInDate(vo.getCheckInDate());
            preAddOrderVO.setCheckOutDate(vo.getCheckOutDate());
            preAddOrderVO.setHotelName(hotel.getHotelName());
            preAddOrderVO.setPrice(room.getRoomPrice());
            preAddOrderVO.setCount(vo.getCount());
            preAddOrderVO.setStore(list.get(0).getStore());
            return DtoUtil.returnSuccess("获取成功", preAddOrderVO);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常", "100513");
        }

    }

    @RequestMapping(value = "/addhotelorder", method = RequestMethod.POST)
    @ResponseBody
    public Dto addHotelOrder(@PathVariable ItripAddHotelOrderVO vo, HttpServletRequest request) {
        String token = request.getHeader("token");
        ItripUser user = validationToken.getCurrentUser(token);
        if (user == null) {
            return DtoUtil.returnFail("token失效，请重登录", "100000");
        }
        if (EmptyUtils.isEmpty(vo)) {
            return DtoUtil.returnFail("不能提交空，请填写订单信息", "100506");
        }
        Map map = new HashMap();
        map.put("hotelId", vo.getHotelId());
        map.put("roomId", vo.getRoomId());
        map.put("startTime", vo.getCheckInDate());
        map.put("endTime", vo.getCheckOutDate());
        map.put("count", vo.getCount());
        try {
            boolean flag = infoService.validateroomstore(map);
            if (!flag) {
                return DtoUtil.returnFail("库存不足", "100507");
            }
            Integer days = DateUtil.getBetweenDates(vo.getCheckInDate(), vo.getCheckOutDate()).size() - 1;
            if (days <= 0) {
                return DtoUtil.returnFail("退房日期必须大于入住日期", "100505");
            }
            ItripHotelOrder order = new ItripHotelOrder();
            BeanUtils.copyProperties(vo, order);
            order.setUserId(user.getId());
            order.setCreatedBy(user.getId());
            order.setCreationDate(new Date());
            List<ItripUserLinkUser> linkUsersList = vo.getLinkUser();
            StringBuilder linkUserName = new StringBuilder();
            int size = linkUsersList.size();
            for (int i = 0; i < size; i++) {
                if (i != size - 1) {
                    linkUserName.append(linkUsersList.get(i).getLinkUserName() + ",");
                } else {
                    linkUserName.append(linkUsersList.get(i).getLinkUserName());
                }
            }

            order.setLinkUserName(linkUserName.toString());
            order.setBookingDays(days);
            order.setOrderType(1);
            if (token.startsWith("token:PC")) {
                order.setBookType(0);
            } else if (token.startsWith("token:MOBILE")) {
                order.setBookType(1);
            } else {
                order.setBookType(2);
            }
            order.setOrderStatus(0);
            //生成订单号：机器码 +日期+（MD5）（商品IDs+毫秒数+1000000的随机数）
            StringBuilder md5String = new StringBuilder();
            md5String.append(order.getHotelId());
            md5String.append(order.getRoomId());
            md5String.append(System.currentTimeMillis());
            md5String.append(Math.random() * 1000000);
            String md5 = MD5.getMd5(md5String.toString(), 6);
            //生成订单编号
            StringBuilder orderNo = new StringBuilder();
            orderNo.append("D1000001");
            orderNo.append(DateUtil.format(new Date(), "yyyyMMddHHmmss"));
            orderNo.append(md5);
            order.setOrderNo(orderNo.toString());
            //计算订单的总金额
            ItripHotelRoom room = infoService.queryRoomById(vo.getRoomId());

            order.setPayAmount(room.getRoomPrice().multiply(BigDecimal.valueOf(days * vo.getCount())));
            Map map1 = infoService.insertOrder(order);
            return DtoUtil.returnSuccess("添加成功", map1);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("生成订单失败", "100505");
        }
    }

    @RequestMapping(value = "/queryOrderById/{orderId}", method = RequestMethod.GET)
    @ResponseBody
    public Dto queryOrderById(@PathVariable Long orerId, HttpServletRequest request) {
        String token = request.getHeader("token");
        ItripUser user = validationToken.getCurrentUser(token);
        if (user == null) {
            return DtoUtil.returnFail("token失效，请重登录", "100000");
        }

        try {
            ItripHotelOrder hotelOrder = infoService.getItripHotelOrderById(orerId);
            if (EmptyUtils.isEmpty(hotelOrder)) {
                return DtoUtil.returnFail("没有查询到相应订单", "100533");
            }
            return DtoUtil.returnSuccess("获取订单成功", hotelOrder);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常", "100534");
        }

    }

    @RequestMapping(value = "/updateorderstatusandpaytype", method = RequestMethod.POST)
    @ResponseBody
    public Dto updateOrderStatuSandPaytype(@RequestBody ItripModifyHotelOrderVO vo, HttpServletRequest request) {
        String token = request.getHeader("token");
        ItripUser user = validationToken.getCurrentUser(token);
        if (user == null) {
            return DtoUtil.returnFail("token失效，请重登录", "100000");
        }
        if (EmptyUtils.isEmpty(vo)) {
            return DtoUtil.returnFail("不能提交空，请填写订单信息", "100523");
        }
        ItripHotelOrder order = new ItripHotelOrder();
        order.setId(vo.getId());
        order.setOrderStatus(2);
        order.setCreatedBy(user.getId());
        order.setModifyDate(new Date());

        try {
            infoService.modifyOrder(order);
            return DtoUtil.returnSuccess("修改订单成功");
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("修改订单失败", "100522");
        }


    }

    @Scheduled(cron = "0 0/5 * * * ?")
    public void flushCancelOrder() {
        try {
            boolean flag = infoService.flushOrderStatus(1);
            System.out.println(flag ? "刷新订单成功" : "刷单失败");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Scheduled(cron = "0 0/30 * * * ?")
    public void flushOrder() {

        try {
            boolean flag = infoService.flushOrderStatus(2);
            System.out.println(flag ? "刷新订单成功" : "刷单失败");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
