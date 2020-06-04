package cn.itrip.controller;

import cn.itrip.beans.dto.Dto;
import cn.itrip.beans.pojo.*;
import cn.itrip.beans.vo.ItripImageVO;
import cn.itrip.beans.vo.ItripLabelDicVO;
import cn.itrip.beans.vo.comment.*;
import cn.itrip.common.*;
import cn.itrip.service.AddComent.CommentService;
import cn.itrip.service.AvgHotelScore.AvgScoreService;
import cn.itrip.service.GetCommentList.GetCommentService;
import cn.itrip.service.GetCount.GetCountService;
import cn.itrip.service.GetHotelDesc.GetHotelDescService;
import cn.itrip.service.TravelType.TravelTypeService;
import cn.itrip.service.getImage.GetImageService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.http.HttpResponse;
import java.util.*;

@Controller
@RequestMapping(" /api/comment")
public class CommentController {
    @Resource
    private TravelTypeService travelTypeService;
    @Resource
    private CommentService commentService;

    @RequestMapping(value = "/gettraveltype", method = RequestMethod.GET)
    @ResponseBody
    public Dto getTravelType() {

        try {
            List<ItripLabelDicVO> travelList = commentService.getTravelType();
            return DtoUtil.returnDataSuccess(travelList);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail(e.getMessage(), ErrorCode.COMMENT_GETTRAVELTYP_FAIL);
        }

    }

    @Resource
    private GetImageService getImageService;

    @RequestMapping(value = "/getimg/{targetId}", method = RequestMethod.GET)
    @ResponseBody
    public Dto getImg(@PathVariable String targetId) {
        if (EmptyUtils.isEmpty(targetId)) {
            return DtoUtil.returnFail("targetId不能为空", ErrorCode.COMMENT_TARGETID_NOTNULL);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("type", 2);
        map.put("targetId", targetId);

        try {
            List<ItripImageVO> image = getImageService.getImage(map);
            return DtoUtil.returnDataSuccess(image);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail(e.getMessage(), "100012");
        }


    }

    @Resource
    private ValidationToken validationToken;


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Dto addComent(@RequestBody ItripAddCommentVO vo, HttpServletRequest request) {
        String token = request.getHeader("token");
        String userAgent = request.getHeader("user-agent");

        try {
            if (!validationToken.validate(userAgent, token)) {
                return DtoUtil.returnFail("token失效，请重登录", "100000");
            }

            if (EmptyUtils.isEmpty(vo.getOrderId())) {
                return DtoUtil.returnFail("新增评论，订单ID不能为空", "100005");
            }

            if (EmptyUtils.isEmpty(vo)) {
                return DtoUtil.returnFail("不能提交空，请填写评论信息", "100004");
            }
            ItripComment itripComment = new ItripComment();
             /* itripComment.setContent(vo.getContent());
            itripComment.setHotelId(vo.getHotelId());
            itripComment.setIsHavingImg(vo.getIsHavingImg());
            itripComment.setPositionScore(vo.getPositionScore());
            itripComment.setFacilitiesScore(vo.getFacilitiesScore());
            itripComment.setHygieneScore(vo.getHygieneScore());
            itripComment.setOrderId(vo.getOrderId());
            itripComment.setServiceScore(vo.getServiceScore());
            itripComment.setProductId(vo.getProductId());
            itripComment.setProductType(vo.getProductType());
            itripComment.setIsOk(vo.getIsOk());
            itripComment.setTripMode(vo.getTripMode());*/
            BeanUtils.copyProperties(vo, itripComment);
            ItripUser currentUser = validationToken.getCurrentUser(token);
            itripComment.setCreatedBy(currentUser.getId());
            itripComment.setCreationDate(new Date());
            itripComment.setUserId(currentUser.getId());

            List<ItripImage> imageList = new ArrayList<>();
            if (vo.getIsHavingImg() == 1) {
                ItripImage[] itripImages = vo.getItripImages();
                int i = 0;
                for (ItripImage img : itripImages) {
                    img.setCreatedBy(currentUser.getId());
                    img.setPosition(i++);
                    img.setType("2");
                    img.setCreationDate(itripComment.getCreationDate());
                    img.setTargetId(itripComment.getId());
                    imageList.add(img);
                }

            }
            commentService.addComent(itripComment, imageList);
            return DtoUtil.returnSuccess("新增评论成功");

        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("新增评论失败", "100003");
        }

    }


    @RequestMapping(value = "/delpic", method = RequestMethod.POST)
    @ResponseBody
    public Dto deletPic(String imgName, HttpServletRequest request) {
        String token = request.getHeader("token");
        String userAgent = request.getHeader("user-agent");

        try {
            if (!validationToken.validate(userAgent, token)) {
                return DtoUtil.returnFail("token失效，请重登录", "100000");
            }
            String path = "/data/comment/upload" + imgName;
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            return DtoUtil.returnSuccess("图片删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("删除图片失败", "100010");
        }
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Dto upload(HttpServletRequest request, HttpResponse response) {
        List<String> dataList = new ArrayList<String>();
        //创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {
            //转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            int fileCount = 0;
            try {
                fileCount = multiRequest.getFileMap().size();
            } catch (Exception e) {
                fileCount = 0;
                return DtoUtil.returnFail("文件大小超限", "100009");
            }
            if (fileCount > 0 && fileCount <= 4) {
                String tokenString = multiRequest.getHeader("token");
                ItripUser itripUser = validationToken.getCurrentUser(tokenString);
                if (null != itripUser) {

                    //取得request中的所有文件名
                    Iterator<String> ite = multiRequest.getFileNames();
                    while (ite.hasNext()) {
                        try {
                            //取得上传文件
                            MultipartFile file = multiRequest.getFile(ite.next());
                            if (file != null) {
                                //取得当前上传文件的文件名称
                                String myFileName = file.getOriginalFilename();
                                String suffixString = FilenameUtils.getExtension(myFileName);
                                //如果名称不为“”,说明该文件存在，否则说明该文件不存在
                                if (myFileName.trim() != ""
                                        &&
                                        (
                                                suffixString.equalsIgnoreCase("jpg")
                                                        || suffixString.equalsIgnoreCase("jpeg")
                                                        || suffixString.equalsIgnoreCase("png")

                                        )) {
                                    //重命名上传后的文件名
                                    //命名规则：用户id+当前时间+随机数
                                    String fileName = itripUser.getId() + "-" + System.currentTimeMillis() + "-" + ((int) (Math.random() * 10000000)) + "." + suffixString;
                                    //定义上传路径
                                    // String path = "/data/comment/upload/" + fileName;
                                    // String path=request.getServletContext().getRealPath("/data/itrip/uploadimg/comment");
                                    String path = "/data/itrip/uploadimg/" + fileName;


                                    File localFile = new File(path, fileName);
                               /* if(!localFile.exists()){
                                    localFile.mkdirs();
                                }*/
                                    file.transferTo(localFile);
                                    dataList.add("http://img.itrip.cn/" + fileName);

                                }

                            }
                        } catch (Exception e) {
                            continue;

                        }
                    }
                    return DtoUtil.returnSuccess("文件上传成功", dataList);
                } else {
                    return DtoUtil.returnFail("文件上传失败", "100006");
                }

            } else {
                return DtoUtil.returnFail("上传的文件数不正确，必须是大于1小于等于4", "100007");
            }

        } else {
            return DtoUtil.returnFail("请求的内容不是上传文件的类型", "100008");
        }

    }

    @Resource
    private GetCommentService getCommentService;

    @RequestMapping(value = "/getcommentlist", method = RequestMethod.POST)
    @ResponseBody
    public Dto getCommentList(@RequestBody ItripSearchCommentVO vo) {
        Map map = new HashMap();
        map.put("hotelId", vo.getHotelId());
        if (vo.getIsHavingImg() == -1) {
            vo.setIsHavingImg(null);
        }
        if (vo.getIsOk() == -1) {
            vo.setIsOk(null);
        }
        map.put("isHavingImg", vo.getIsHavingImg());
        map.put("isOk", vo.getIsOk());
        Integer pageNo = vo.getPageNo() == null ? 1 : vo.getPageNo();
        map.put("pageNo", pageNo);
        Integer pageSize = vo.getPageSize() == null ? 5 : vo.getPageSize();
        map.put("pageSize", pageSize);

        try {
            Page<ItripListCommentVO> page = getCommentService.getCommentList(map);
            return DtoUtil.returnDataSuccess(page);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("获取评论列表错误", "100020");
        }


    }

    @Resource
    private GetHotelDescService descService;

    @RequestMapping(value = "/gethoteldesc/{hotelId}", method = RequestMethod.GET)
    @ResponseBody
    public Dto getHotelDesc(@PathVariable String hotelId) {
        if(EmptyUtils.isEmpty(hotelId)){
            return DtoUtil.returnFail("酒店id不能为空", "100002");
        }

        try {
            ItripScoreCommentVO vo = commentService.getHotelScore(Long.parseLong(hotelId));
            return DtoUtil.returnDataSuccess(vo);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("获取酒店相关信息错误", "100021");
        }

    }

    @Resource
    private AvgScoreService scoreService;

    @RequestMapping(value = "/gethotelscore/{hotelId}", method = RequestMethod.GET)
    @ResponseBody
    public Dto getHotelScore(@PathVariable String hotelId) {
        if (EmptyUtils.isEmpty(hotelId)) {
            return DtoUtil.returnFail("酒店id不能为空", "100002");
        }

        try {
            ItripScoreCommentVO vo = scoreService.getCommentAvgScore(Long.parseLong(hotelId));
            return DtoUtil.returnSuccess("获取评分成功", vo);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("获取评分失败", "100001 ");
        }
    }

    @Resource
    private GetCountService getCountService;

    @RequestMapping(value = "/getcount/{hotelId}", method = RequestMethod.GET)
    @ResponseBody
    public Dto getCount(@PathVariable String hotelId) {
        if (EmptyUtils.isEmpty(hotelId)) {
            return DtoUtil.returnFail("酒店Id不能为空", "100018");
        }
        Map result = new HashMap();
        Map map = new HashMap();
        map.put("hotelId", hotelId);
        try {
            Integer totalCount = getCountService.queryCount(map);
            result.put("allcomment", totalCount);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("获取酒店总评论数失败", "100014");
        }

        map.put("isHavingImg", 1);
        try {
            Integer imgCount = getCountService.queryCount(map);
            result.put("havingimg", imgCount);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("获取酒店有图片评论数失败", "100015");
        }

        map.remove("isHavingImg");
        map.put("isOk", 0);
        try {
            Integer improvCount = getCountService.queryCount(map);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail(" 获取酒店有待改善评论数失败", "100016");
        }

        map.put("isOk", 1);
        try {
            Integer okCount = getCountService.queryCount(map);
            result.put("isok", okCount);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail(" 获取酒店值得推荐评论数失败", "100017");
        }

        return DtoUtil.returnSuccess("获取评论数成功", result);

    }

}
