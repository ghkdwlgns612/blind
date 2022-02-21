package blind.back.blind.domain.notification.repository;

import blind.back.blind.domain.notification.domain.Notification;
import blind.back.blind.domain.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NotificationRepository extends JpaRepository<Notification,Long> {

    @Query("SELECT count(n) FROM Notification n WHERE n.deletedAt IS NULL AND n.user.id = :userId")
    Integer countNotificationsByUserIdAndDeletedAt(Long userId);

    @Query("SELECT n FROM Notification n WHERE n.user = :user ORDER BY n.createdAt DESC")
    Page<Notification> findAllByUser(Pageable pageable, User user);
}
