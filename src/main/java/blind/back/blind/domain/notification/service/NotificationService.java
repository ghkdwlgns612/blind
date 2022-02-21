package blind.back.blind.domain.notification.service;

import blind.back.blind.domain.comment.domain.Comment;
import blind.back.blind.domain.notification.domain.Notification;
import blind.back.blind.domain.notification.domain.dto.Type;
import blind.back.blind.domain.notification.domain.dto.request.PageNotificationRequest;
import blind.back.blind.domain.notification.domain.dto.response.NotificationCountResponse;
import blind.back.blind.domain.notification.domain.dto.response.PageNotificationResponse;
import blind.back.blind.domain.notification.repository.NotificationRepository;
import blind.back.blind.domain.post.domain.Post;
import blind.back.blind.domain.user.domain.User;
import blind.back.blind.domain.user.repository.UserRepository;
import blind.back.blind.global.error.Exception.NotFoundUserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationCountResponse getNotReadNotifications(String userId) {
        return NotificationCountResponse.builder()
                .number(notificationRepository.countNotificationsByUserIdAndDeletedAt(Long.valueOf(userId)))
                .build();
    }

    public List<PageNotificationResponse> getPageNotifications(PageNotificationRequest request, String userId) {
        List<PageNotificationResponse> response = new ArrayList<>();
        Optional<User> user = userRepository.findById(Long.valueOf(userId));
        if (user.isEmpty()) {
            throw new NotFoundUserException();
        }
        notificationRepository.findAllByUser(PageRequest.of(request.getPage(), request.getSize()), user.get())
                .forEach(n -> response.add(getPageNotificationBuilder(n)));
        return response;
    }

    public void createNotification(Comment comment, User user, Post post, String type) {
        Notification notification = getNotificationBuilder(comment, user, post, type);
        notificationRepository.save(notification);
        log.info("알림이 생성되었습니다.");
    }

    private Notification getNotificationBuilder(Comment comment, User user, Post post, String type) {
        return Notification.builder()
                .comment(comment)
                .post(post)
                .type(Type.valueOf(type))
                .user(user)
                .build();
    }

    public void deleteNotification(Long notificationId) throws Exception {
        Optional<Notification> noti = notificationRepository.findById(notificationId);
        if (noti.isEmpty()) {
            throw new Exception("존재하지 않는 알림입니다.");
        }
        if (noti.get().getDeletedAt() != null) {
            throw new Exception("이미 삭제 된 알림입니다.");
        }
        noti.get().setDeletedAt(LocalDateTime.now());
        notificationRepository.save(noti.get());
        log.info("알림이 확인되었습니다.");
    }

    private PageNotificationResponse getPageNotificationBuilder(Notification notification) {
        return PageNotificationResponse.builder()
                .parent_id(notification.getComment().getParentId())
                .notification_id(notification.getId())
                .comment_content(notification.getComment().getContent())
                .created_at(notification.getCreatedAt())
                .post_id(notification.getPost().getId())
                .deleted_at(notification.getDeletedAt())
                .type(notification.getType().getType())
                .build();
    }
}
