package com.software.nju.Service;


import com.software.nju.Bean.Config;
import com.software.nju.Dao.ConfigDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ConfigService {

    @Autowired
    ConfigDao configDao;

    public Config findConfigByVisual(int visualId){
        return configDao.findConfigByVisualId(visualId);
    }

    public int saveConfig(Config config)  {
        return configDao.save(config).getId();
    }
    public int remove(Integer id){
        return configDao.removeConfigById(id);
    }
}
