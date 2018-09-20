package com.miller.code.gererator.service;

import com.miller.code.gererator.config.CodeGeneratorConfig;
import com.miller.code.gererator.config.TemplateEnum;

/**
 * 生成代码主要逻辑接口
 */
public interface CodeGenerator {

	/**
	 * 代码生成主要逻辑
	 *
	 * @param tableName 表名
	 * @param modelName 自定义实体类名, 为null则默认将表名下划线转成大驼峰形式
	 * @param config 表生成配置文件
	 */
	void genCode(TemplateEnum templateEnum , String tableName, String modelName, CodeGeneratorConfig config);
}
