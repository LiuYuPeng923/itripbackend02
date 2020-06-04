package cn.itrip.service.image;

import cn.itrip.beans.pojo.ItripImage;
import cn.itrip.dao.image.ItripImageMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ImageServiceImpl implements ImageService {
    @Resource
    private ItripImageMapper imageMapper;

    @Override
    public List<ItripImage> getImageList(Map map) throws Exception {
        return imageMapper.getItripImageListByMap(map);
    }
}
