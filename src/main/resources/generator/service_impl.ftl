package ${basePackage}.${modelName?uncap_first}.service.impl;


import org.springframework.stereotype.Service;
import com.miller.core.BaseAbstractService;
import ${basePackage}.${modelName?uncap_first}.model.${modelName};
import ${basePackage}.${modelName?uncap_first}.service.${modelName}Service;
import ${basePackage}.${modelName?uncap_first}.mapper.${modelName}Mapper;

/**
 *
 * Created by ${author} on ${now?string("yyyy-MM-dd")}
 */
@Service
public class ${modelName}ServiceImpl extends BaseAbstractService<${modelName},${modelName}Mapper> implements ${modelName}Service {


}
