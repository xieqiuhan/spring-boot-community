package cn.edith.demo.community.service;

import cn.edith.demo.community.dto.NotificationDTO;
import cn.edith.demo.community.dto.PaginationDTO;
import cn.edith.demo.community.dto.QuestionDTO;
import cn.edith.demo.community.enums.NotificationStatusEnum;
import cn.edith.demo.community.enums.NotificationTypeEnum;
import cn.edith.demo.community.exception.CustomizeErrorCode;
import cn.edith.demo.community.exception.CustomizeException;
import cn.edith.demo.community.mapper.NotificationMapper;
import cn.edith.demo.community.mapper.UserMapper;
import cn.edith.demo.community.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private UserMapper userMapper;
    public PaginationDTO list(Long id, Integer page, Integer size) {
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO();
        Integer totalPage;
        NotificationExample example = new NotificationExample();
        example.createCriteria().andReceiverEqualTo(id);
        Integer totalCount =(int) notificationMapper.countByExample(example);

        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        paginationDTO.setPagination(totalPage, page);

        Integer offset = size * (page - 1);
        NotificationExample exampleList = new NotificationExample();

        exampleList.createCriteria().andReceiverEqualTo(id);
        exampleList.setOrderByClause("gmt_create desc");
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(exampleList,new RowBounds(offset,size));
        List<NotificationDTO> notificationDTOLists = new ArrayList<>();
        if(notifications.size() == 0){
            return  paginationDTO;
        }
        for(Notification notification:notifications){
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOLists.add(notificationDTO);
        }

        paginationDTO.setData(notificationDTOLists);
        return  paginationDTO;
    }

    public Long unreadCount(Long id) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(id)
                .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(notificationExample);
    }

    public NotificationDTO read(Long id, User user) {
       Notification notification = notificationMapper.selectByPrimaryKey(id);
       if(notification == null) {
           throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
       }if(!Objects.equals(notification.getReceiver() , user.getId())){
           throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
       }

       notification.setStatus(NotificationStatusEnum.READ.getStatus());
       notificationMapper.updateByPrimaryKey(notification);
        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification,notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }
}
