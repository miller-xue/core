package ${basePackage}.${modelName?uncap_first}.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.miller.core.BaseController;
import ${basePackage}.${modelName?uncap_first}.model.${modelName};
import ${basePackage}.${modelName?uncap_first}.service.${modelName}Service;

/**
 *
 * Created by ${author} on ${now?string("yyyy-MM-dd")}.
 */
@Controller
@RequestMapping("/${modelName?uncap_first }")
public class ${modelName}Controller extends BaseController<${modelName}, ${modelName}Service> {


}
