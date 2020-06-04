package cn.itrip.service.TargetImg;

import cn.itrip.beans.vo.ItripImageVO;
import cn.itrip.dao.image.ItripImageMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class RoomImgServiceImpl implements RoomImgService {
    @Resource
    private ItripImageMapper imageMapper;

    @Override
    public List<ItripImageVO> queryImg(Map map) throws Exception {
        return imageMapper.getItripImageListByMap(map);
    }
}
