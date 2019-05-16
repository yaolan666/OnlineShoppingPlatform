package com.youzhi.model;

import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.generator.Generator;
import com.jfinal.plugin.druid.DruidPlugin;
import com.youzhi.config.UserConfig;

import javax.sql.DataSource;

public class _JFinalUserGenerator {
    public static DataSource getDataSource(){
        DruidPlugin druidPlugin = UserConfig.createDruidPlugin();
        druidPlugin.start();
        return  druidPlugin.getDataSource();
    }

    public static void main(String[] args) {
        String baseModelPackageName = "com.youzhi.model.base";
        String baseModelOutputDir = PathKit.getWebRootPath()+"/src/main/java/com/youzhi/model/base";
        String modelPackageName = "com.youzhi.model";
        String modelOutputDir = baseModelOutputDir+"/..";
        // 创建生成器
        Generator generator = new Generator(getDataSource(), baseModelPackageName, baseModelOutputDir, modelPackageName, modelOutputDir);

        // 配置是否生成备注
        generator.setGenerateRemarks(false);

        // 设置数据库方言
        generator.setDialect(new MysqlDialect());

        // 设置是否生成链式 setter 方法
        generator.setGenerateChainSetter(false);

        // 添加不需要生成的表名
        generator.addExcludedTable("adv");

        // 设置是否在 Model 中生成 dao 对象
        generator.setGenerateDaoInModel(false);

        // 设置是否生成字典文件
        generator.setGenerateDataDictionary(false);

        // 设置需要被移除的表名前缀用于生成modelName。例如表名 "osc_user"，移除前缀 "osc_"后生成的model名为 "User"而非 OscUser
        generator.setRemovedTableNamePrefixes("t_");

        // 生成
        generator.generate();
    }
}
