package com.smart.classroom.misc.domain.biz.editor.service;

import com.smart.classroom.misc.domain.biz.editor.model.EditorModel;
import com.smart.classroom.misc.domain.biz.editor.repository.EditorRepository;
import com.smart.classroom.misc.domain.biz.reader.model.ReaderModel;
import com.smart.classroom.misc.utility.exception.UtilException;
import com.smart.classroom.misc.utility.util.CryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 创建小编领域服务
 *
 * @author lishuang
 * @date 2023-05-12
 */
@Service
public class EditorRegisterDomainService {

    @Autowired
    EditorRepository editorRepository;

    /**
     * 小编注册
     */
    public EditorModel register(String username, String password, String workNo) {
        EditorModel editorModel = editorRepository.queryByUsername(username);
        if (editorModel != null) {
            throw new UtilException("{}对应的用户名已存在", username);
        }
        editorModel = editorRepository.queryByWorkNo(workNo);
        if (editorModel != null) {
            throw new UtilException("{}对应的工号已存在", workNo);
        }

        //密码简单使用 md5加密
        String encodedPassword = CryptUtil.bCryptEncode(password);

        editorModel = EditorModel
                .builder()
                .createTime(new Date())
                .updateTime(new Date())
                .username(username)
                .password(encodedPassword)
                .workNo(workNo)
                .build();

        editorModel = editorRepository.insert(editorModel);

        //TODO: 发送注册成功的领域事件。

        return editorModel;
    }


    public EditorModel login(String username, String password) {
        EditorModel editorModel = editorRepository.queryByUsername(username);
        if (editorModel == null) {
            throw new UtilException("用户名或密码错误");
        }

        boolean b = CryptUtil.bCryptMatches(password, editorModel.getPassword());
        if (!b) {
            throw new UtilException("用户名或密码错误");
        }

        //TODO: 发送登录成功的领域事件。

        return editorModel;
    }

}
