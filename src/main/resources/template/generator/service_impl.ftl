package ${basePackage}.${modelName?uncap_first}.${templateEnum.SERVICE_IMPL.targetPkg};


import org.springframework.stereotype.Service;
import com.miller.framework.core.common.BaseAbstractService;
import ${basePackage}.${modelName?uncap_first}.model.${modelName};
import ${basePackage}.${modelName?uncap_first}.${templateEnum.SERVICE.targetPkg}.${modelName}Service;
import ${basePackage}.${modelName?uncap_first}.dao.${modelName}Mapper;

/**
 *
 * Created by ${author} on ${now?string("yyyy-MM-dd")}
 */
@Service
public class ${modelName}${templateEnum.SERVICE_IMPL.suffix} extends BaseAbstractService<${modelName},${modelName}Mapper> implements ${modelName}${templateEnum.SERVICE.suffix}  {


}
