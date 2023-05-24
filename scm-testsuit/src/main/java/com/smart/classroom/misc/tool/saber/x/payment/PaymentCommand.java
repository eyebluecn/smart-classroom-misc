package com.smart.classroom.misc.tool.saber.x.payment;


import com.smart.classroom.misc.domain.biz.payment.enums.PayMethod;
import com.smart.classroom.misc.domain.biz.payment.enums.PayStatus;
import com.smart.classroom.misc.tool.saber.command.SaberCommand;
import com.smart.classroom.misc.tool.saber.support.SaberHelper;
import com.smart.classroom.misc.utility.model.Pair;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 直接运行该文件，会生成对应的DO，Mapper文件。
 */
public class PaymentCommand {
    public static void main(String[] args) {

        /*
         * 	scm_payment
         */
        SaberCommand saberCommand = new SaberCommand("scm_payment");

        //模糊筛选
        saberCommand.setFilterLikeFields(new ArrayList<String>() {{
            //下划线风格，驼峰均可以


        }});

        //等号筛选
        saberCommand.setFilterEqualFields(new ArrayList<String>() {{
            //下划线风格，驼峰均可以

            add("order_no");
            add("method");
            add("third_transaction_no");
            add("status");
        }});

        //排序
        saberCommand.setFilterOrderFields(new ArrayList<String>() {{
            //下划线风格，驼峰均可以

        }});

        //枚举定义
        saberCommand.setEnumFieldMap(new HashMap<String, Pair<Class<? extends Enum<?>>, String>>() {{
            put("method", new Pair<>(PayMethod.class, PayMethod.ALIPAY.name()));
            put("status", new Pair<>(PayStatus.class, PayStatus.UNPAID.name()));
        }});

        SaberHelper.run(saberCommand);
    }
}
