package springboot.demo.mapper;

import org.apache.ibatis.annotations.Param;import springboot.demo.model.Notification;import java.util.List;

public interface NotificationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Notification record);

    int insertSelective(Notification record);

    Notification selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Notification record);

    int updateByPrimaryKey(Notification record);

    List<Notification> selectAllByReceiver(@Param("receiver") Long receiver);


    Long countByReceiverAndStatus(@Param("receiver")Long receiver);



}