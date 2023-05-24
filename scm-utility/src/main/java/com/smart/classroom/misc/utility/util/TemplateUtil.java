package com.smart.classroom.misc.utility.util;


import lombok.NonNull;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.StringWriter;
import java.util.Map;


/**
 * Velocity语法
 * 专门用来渲染模板的。
 */
public class TemplateUtil {


    //传入模板字符串，和参数，自动填写完成！
    public static String render(@NonNull String template, @NonNull Map<String, Object> map) {

        VelocityContext context = new VelocityContext();

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() != null) {
                context.put(entry.getKey(), entry.getValue());
            }
        }

        StringWriter writer = new StringWriter();

        Velocity.evaluate(context, writer, "", template);

        return writer.toString();
    }


}
