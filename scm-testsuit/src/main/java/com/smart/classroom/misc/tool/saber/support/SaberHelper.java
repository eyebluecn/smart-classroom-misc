package com.smart.classroom.misc.tool.saber.support;

import com.smart.classroom.misc.tool.saber.artwork.*;
import com.smart.classroom.misc.tool.saber.artwork.base.Artwork;
import com.smart.classroom.misc.tool.saber.command.SaberCommand;
import com.smart.classroom.misc.tool.saber.config.SaberConfig;
import com.smart.classroom.misc.tool.saber.meta.SaberColumn;
import com.smart.classroom.misc.tool.saber.meta.SaberTable;
import com.smart.classroom.misc.utility.util.PathUtil;
import com.smart.classroom.misc.utility.util.StringUtil;
import com.smart.classroom.misc.utility.util.TemplateUtil;
import lombok.Data;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 用来协助连接数据库以及生成文件的类
 *
 * @author lishuang 2023-05-11
 */
@Data
@Slf4j
public class SaberHelper {

    /**
     * 接收用户的指令,根据这个指令来生成需要的文件。
     */
    private List<SaberCommand> saberCommands = new ArrayList<>();

    /**
     * 需要生成的文件。
     */
    private List<Artwork> artworks = new ArrayList<Artwork>() {{

        add(new DOArtwork());
        add(new BaseMapperJavaArtwork());
        add(new BaseMapperXmlArtwork());
        add(new MapperJavaArtwork());
        add(new MapperXmlArtwork());
    }};


    /**
     * 获取所有表的元信息。
     */
    @SneakyThrows
    private List<SaberTable> fetchTableMeta() {

        //构建一个数据源连接
        Class.forName(com.mysql.jdbc.Driver.class.getName());

        //建立数据库对象
        Connection connection = DriverManager.getConnection(
                SaberConfig.JDBC_URL,
                SaberConfig.JDBC_USERNAME,
                SaberConfig.JDBC_PASSWORD);

        List<SaberTable> tables = new ArrayList<>();

        DatabaseMetaData databaseMetaData = connection.getMetaData();

        ResultSet resultSet = databaseMetaData.getTables(null, "%", "%", new String[]{"TABLE"});
        while (resultSet.next()) {

            String tableName = resultSet.getString("TABLE_NAME");
            String tableRemarks = resultSet.getString("REMARKS");

            SaberTable table = new SaberTable();
            table.setName(tableName);
            table.setRemark(tableRemarks);

            ResultSet columnResultSet = databaseMetaData.getColumns(null, "%", tableName, "%");


            while (columnResultSet.next()) {

                String columnName = columnResultSet.getString("COLUMN_NAME");
                String remarks = columnResultSet.getString("REMARKS");
                String typeName = columnResultSet.getString("TYPE_NAME");
                String defaultValue = columnResultSet.getString("COLUMN_DEF");

                table.getColumns().add(new SaberColumn(columnName, remarks, typeName, defaultValue));

            }

            tables.add(table);
        }

        if (!connection.isClosed()) {
            connection.close();
        }

        return tables;
    }

    /**
     * 获取到某个文件中的内容
     */
    @SneakyThrows
    public static String getResourceFileContent(String fileName) {

        return PathUtil.getResourceContent("saber/" + fileName);
    }


    /**
     * 某个文件中写入内容。
     */
    @SneakyThrows
    public static void writeFileContent(String fileFullPath, String content) {

        FileWriter fileWriter = new FileWriter(fileFullPath);

        IOUtils.write(content, fileWriter);

        fileWriter.close();
    }

    /**
     * 生成一个artwork.
     */
    public void generateArtwork(@NonNull SaberTable saberTable, @NonNull SaberCommand saberCommand, @NonNull Artwork artwork) {

        log.info("开始处理表：{} 使用模板：{}", saberCommand.getTableName(), artwork.getTemplateFileName());

        //表中的有些字段，可能需要按照saberCommand中的自定义配置来重新指定
        saberTable.prepare(saberCommand);

        //初始化artwork.
        artwork.init(saberTable, saberCommand);

        Map<String, Object> params = new HashMap<>();
        params.put(Artwork.class.getSimpleName().toLowerCase(), artwork);

        //检查文件是否存在
        if (PathUtil.isExist(artwork.destAbsolutePath())) {

            if (artwork.isOverwrite()) {
                String formContent = getResourceFileContent(artwork.getTemplateFileName());
                formContent = TemplateUtil.render(formContent, params);
                writeFileContent(artwork.destAbsolutePath(), formContent);

                log.info("已经存在，直接覆盖 {}", artwork.destAbsolutePath());
            } else {
                log.warn("文件已存在，放弃自动生成。{}", artwork.destAbsolutePath());
            }

        } else {

            String formContent = getResourceFileContent(artwork.getTemplateFileName());
            formContent = TemplateUtil.render(formContent, params);
            writeFileContent(artwork.destAbsolutePath(), formContent);

            log.info(" 新生成 ：{}", artwork.destAbsolutePath());
        }


    }

    public void process() {

        /**
         * 验证每个指令的逻辑是否合理。添加上一些默认值
         */
        this.saberCommands.forEach(SaberCommand::prepare);


        /**
         * 获取系统中所有的表元信息和字段元信息
         */
        List<SaberTable> tables = this.fetchTableMeta();
        log.info("系统中包含以下表：");
        tables.forEach(saberTable -> {
            log.info(saberTable.getName());
        });


        /**
         * 选出我们需要的表进行生成。
         */
        tables.forEach(saberTable -> {
            this.saberCommands.forEach(saberCommand -> {
                if (StringUtil.equals(saberTable.getName(), saberCommand.getTableName())) {

                    //每种模板都生成。
                    for (Artwork artwork : this.artworks) {
                        this.generateArtwork(saberTable, saberCommand, artwork);
                    }

                }
            });
        });

        log.info("执行完毕！");
    }


    public void start() {

        //在这里进行全局异常捕获
        try {
            this.process();
        } catch (Throwable throwable) {
            log.error("出错啦！");
            log.error(ExceptionUtils.getRootCauseMessage(throwable));
            throwable.printStackTrace();
        }


    }

    //只运行一条命令
    public static void run(SaberCommand saberCommand) {
        SaberHelper saberHelper = new SaberHelper();
        saberHelper.setSaberCommands(new ArrayList<SaberCommand>() {{
            add(saberCommand);
        }});
        saberHelper.start();
    }

}
