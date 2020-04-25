package springboot.demo.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.xml.internal.messaging.saaj.packaging.mime.util.BEncoderStream;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.demo.dto.NotificationDTO;
import springboot.demo.enums.NotificationEnum;
import springboot.demo.exception.NotificationException;
import springboot.demo.mapper.NotificationMapper;
import springboot.demo.model.Notification;
import springboot.demo.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;
    public PageInfo listByUserId(Integer page, Integer size, Long id) {
        PageHelper.startPage(page,size);
        PageHelper.orderBy("gmt_create desc");
        List<Notification> notifications = notificationMapper.selectAllByReceiver(id);
        PageInfo pageInfo=new PageInfo(notifications);
        return pageInfo;
    }

    public List<NotificationDTO> toNotificationDTO(List<Notification> list) {
        List<NotificationDTO> notificationDTOS=new ArrayList<>();
        for (Notification notification:list){
            NotificationDTO n = new NotificationDTO();
            BeanUtils.copyProperties(notification, n);
            n.setTypeName(NotificationEnum.getTypeName(notification.getType()));
            notificationDTOS.add(n);
        }
        return notificationDTOS;
    }


    public Long unreadCount(Long id) {
        Long unreadCount = notificationMapper.countByReceiverAndStatus(id);
        return unreadCount;
    }

    public NotificationDTO read(User user, Long id) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification==null){
            throw new NotificationException("消息莫非是不翼而飞了？",2007);
        }
        if (user.getId()!=notification.getReceiver()){
            throw new NotificationException("兄弟你这是读别人的信息呢？",2006);
        }
        notification.setStatus(1);
        notificationMapper.updateByPrimaryKeySelective(notification);
        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification,notificationDTO);
        notificationDTO.setTypeName(NotificationEnum.getTypeName(notification.getType()));
        return notificationDTO;
    }
}
