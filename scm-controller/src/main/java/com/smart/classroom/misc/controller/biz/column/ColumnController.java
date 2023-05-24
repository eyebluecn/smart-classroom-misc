package com.smart.classroom.misc.controller.biz.column;


import com.smart.classroom.misc.controller.auth.Feature;
import com.smart.classroom.misc.controller.auth.FeatureType;
import com.smart.classroom.misc.controller.base.BaseController;
import com.smart.classroom.misc.controller.base.result.WebResult;
import com.smart.classroom.misc.controller.biz.column.response.RichColumnDTO;
import com.smart.classroom.misc.facade.biz.author.AuthorReadFacade;
import com.smart.classroom.misc.facade.biz.author.response.AuthorDTO;
import com.smart.classroom.misc.facade.biz.column.ColumnReadFacade;
import com.smart.classroom.misc.facade.biz.column.ColumnWriteFacade;
import com.smart.classroom.misc.facade.biz.column.request.ColumnPageRequest;
import com.smart.classroom.misc.facade.biz.column.request.OmnibusRequest;
import com.smart.classroom.misc.facade.biz.column.response.ColumnDTO;
import com.smart.classroom.misc.facade.biz.editor.response.EditorDTO;
import com.smart.classroom.misc.facade.biz.quote.ColumnQuoteReadFacade;
import com.smart.classroom.misc.facade.biz.quote.response.ColumnQuoteDTO;
import com.smart.classroom.misc.facade.biz.reader.response.ReaderDTO;
import com.smart.classroom.misc.facade.common.resposne.PagerResponse;
import com.smart.classroom.misc.utility.exception.ExceptionCode;
import com.smart.classroom.misc.utility.exception.UtilException;
import com.smart.classroom.subscription.facade.biz.subscription.SubscriptionReadFacade;
import com.smart.classroom.subscription.facade.biz.subscription.request.ReaderColumnQueryRequest;
import com.smart.classroom.subscription.facade.biz.subscription.response.SubscriptionDTO;
import lombok.SneakyThrows;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 专栏的controller.
 *
 * @author lishuang
 * @date 2023-05-13
 */
@RestController
@RequestMapping("/api/column")
public class ColumnController extends BaseController {

    @DubboReference
    ColumnWriteFacade columnWriteFacade;

    @DubboReference
    ColumnReadFacade columnReadFacade;

    @DubboReference
    AuthorReadFacade authorReadFacade;

    @DubboReference
    ColumnQuoteReadFacade columnQuoteReadFacade;

    @DubboReference
    SubscriptionReadFacade subscriptionReadFacade;

    /**
     * 演示接口。
     * 创建专栏等相关所有实体。
     */
    @Feature(FeatureType.EDITOR_LOGIN)
    @RequestMapping("/omnibus")
    public WebResult<?> omnibus(
            @RequestParam String authorName,
            @RequestParam String columnName,
            @RequestParam int columnPrice
    ) {

        EditorDTO editorDTO = checkLoginEditor();
        OmnibusRequest omnibusRequest = new OmnibusRequest(
                authorName,
                columnName,
                editorDTO.getId(),
                columnPrice
        );

        columnWriteFacade.omnibus(omnibusRequest);

        return success();
    }

    /**
     * 专栏列表
     */
    @Feature({FeatureType.READER_LOGIN, FeatureType.AUTHOR_LOGIN, FeatureType.EDITOR_LOGIN})
    @RequestMapping("/page")
    public WebResult<?> page(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "20") int pageSize,
            @RequestParam(required = false) String orderCreateTime,
            @RequestParam(required = false) String orderUpdateTime,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long authorId,
            @RequestParam(required = false) String status
    ) {

        ReaderDTO readerDTO = findLoginReader();

        ColumnPageRequest columnPageRequest = new ColumnPageRequest(
                pageNum,
                pageSize,
                orderCreateTime,
                orderUpdateTime,
                name,
                authorId,
                status
        );


        PagerResponse<ColumnDTO> columnDTOPagerResponse = columnReadFacade.list(columnPageRequest);

        List<RichColumnDTO> list = new ArrayList<>();
        for (ColumnDTO columnDTO : columnDTOPagerResponse.getData()) {


            AuthorDTO authorDTO = authorReadFacade.queryById(columnDTO.getAuthorId());
            ColumnQuoteDTO columnQuoteDTO = columnQuoteReadFacade.queryByColumnId(columnDTO.getId());

            SubscriptionDTO subscriptionDTO = null;
            if (readerDTO != null) {
                subscriptionDTO = subscriptionReadFacade.queryByColumnIdAndReaderId(new ReaderColumnQueryRequest(
                        readerDTO.getId(),
                        columnDTO.getId()
                ));
            }


            RichColumnDTO richColumnDTO = new RichColumnDTO(
                    columnDTO,
                    authorDTO,
                    columnQuoteDTO,
                    subscriptionDTO
            );

            list.add(richColumnDTO);
        }

        PagerResponse<RichColumnDTO> richColumnDetailDTOPagerResponse = new PagerResponse<>(
                columnDTOPagerResponse.getPageNum(),
                columnDTOPagerResponse.getPageSize(),
                columnDTOPagerResponse.getTotalItems(),
                list
        );


        return success(richColumnDetailDTOPagerResponse);
    }


    /**
     * 专栏详情
     */
    @SneakyThrows
    @Feature({FeatureType.READER_LOGIN, FeatureType.AUTHOR_LOGIN, FeatureType.EDITOR_LOGIN})
    @RequestMapping("/detail")
    public WebResult<?> detail(
            @RequestParam long id
    ) {

        ColumnDTO columnDTO = columnReadFacade.queryById(id);
        if (columnDTO == null) {
            throw new UtilException(ExceptionCode.NOT_FOUND, "id={}对应的专栏不存在", id);
        }

        return success(columnDTO);
    }


}
