package com.software.nju.Service;


import com.mongodb.internal.validator.CollectibleDocumentFieldNameValidator;
import com.software.nju.Bean.Config;
import com.software.nju.Dao.ConfigDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

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
}
