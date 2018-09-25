package ${basePackage}.${modelName?uncap_first}.${templateEnum.CONTROLLER.targetPkg};

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.miller.framework.core.common.BaseController;
import ${basePackage}.${modelName?uncap_first}.model.${modelName};
import ${basePackage}.${modelName?uncap_first}.${templateEnum.SERVICE.targetPkg}.${modelName}Service;

/**
 *
 * Created by ${author} on ${now?string("yyyy-MM-dd")}.
 */
@Controller
@RequestMapping("/${modelName?uncap_first }")
public class ${modelName}${templateEnum.CONTROLLER.suffix} extends BaseController<${modelName}, ${modelName}${templateEnum.SERVICE.suffix}> {


}
