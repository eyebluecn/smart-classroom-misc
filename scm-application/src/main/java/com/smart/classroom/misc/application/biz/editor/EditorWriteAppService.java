package com.smart.classroom.misc.application.biz.editor;

import com.smart.classroom.misc.domain.biz.editor.model.EditorModel;
import com.smart.classroom.misc.domain.biz.editor.repository.EditorRepository;
import com.smart.classroom.misc.domain.biz.editor.service.EditorRegisterDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lishuang
 * @date 2023-05-12
 */
@Slf4j
@Service
public class EditorWriteAppService {

    @Autowired
    EditorRepository editorRepository;

    @Autowired
    EditorRegisterDomainService editorRegisterDomainService;

    public EditorModel register(String username, String password, String workNo) {


        EditorModel editorModel = editorRegisterDomainService.register(username, password, workNo);

        return editorModel;

    }

    public EditorModel queryById(long editorId) {
        return editorRepository.queryById(editorId);
    }

    public EditorModel login(String username, String password) {
        EditorModel editorModel = editorRegisterDomainService.login(username, password);

        return editorModel;
    }

    public EditorModel queryByUsername(String username) {
        return editorRepository.queryByUsername(username);
    }
}
