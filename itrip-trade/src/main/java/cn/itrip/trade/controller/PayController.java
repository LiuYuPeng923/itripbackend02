package cn.itrip.trade.controller;

import cn.itrip.beans.pojo.ItripHotelOrder;
import cn.itrip.trade.bean.AlipayBean;
import cn.itrip.trade.bean.AlipayConfig;
import cn.itrip.trade.service.AliPayService;
import cn.itrip.trade.service.OrderService;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class PayController {
    @Resource
    private OrderService orderService;
    @Resource
    private AlipayConfig alipayConfig;
    @Resource
    private AliPayService payService;

    @RequestMapping(value = "/prepay/{orderNo}", method = RequestMethod.GET)
    public String prePay(@PathVariable String orderNo, Model model) {
        try {
            ItripHotelOrder order = orderService.loadItripHotelOrder(orderNo);
            model.addAttribute("orderNo", orderNo);
            model.addAttribute("hotelName", order.getHotelName());
            model.addAttribute("payAmount", order.getPayAmount());
            model.addAttribute("roomId", order.getRoomId());
            model.addAttribute("count", order.getCount());
            return "pay";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "notfound";
    }

    @RequestMapping(value = "/pay", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
    @ResponseBody
    public String pay(AlipayBean bean) {

        try {
            return payService.alipay(bean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/notify", method = RequestMethod.POST)
    public String trackPaymentStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParam = request.getParameterMap();
        for (Iterator<String> iter = requestParam.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParam.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        boolean signVerified = AlipaySignature.rsaCheckV1(params, alipayConfig.getAlipayPublicKey(), alipayConfig.getCharset(), alipayConfig.getSignType()); //调用SDK验证签名
        if (signVerified) {
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
            if (trade_status.equals("TRADE_FINISHED")) {
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序
                orderService.paySuccess(out_trade_no, 1, trade_no);
                //注意：
                //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
            } else if (trade_status.equals("TRADE_SUCCESS")) {
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序
                orderService.paySuccess(out_trade_no, 1, trade_no);
                //注意：
                //付款完成后，支付宝系统发送该交易状态通知
            }
            return "success";
        } else {
            return "failure";
        }
    }

    @RequestMapping(value = "/return", method = RequestMethod.GET)
    public void success(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, alipayConfig.getAlipayPublicKey(), alipayConfig.getCharset(), alipayConfig.getSignType()); //调用SDK验证签名
        if (signVerified) {
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
            Long id = orderService.loadItripHotelOrder(out_trade_no).getId();
            response.sendRedirect(String.format(alipayConfig.getPaymentSuccessUrl(), out_trade_no, id));
        } else {
            response.sendRedirect(String.format(alipayConfig.getPaymentFailureUrl()));
        }
    }
}
