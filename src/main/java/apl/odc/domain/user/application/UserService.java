package apl.odc.domain.user.application;

import apl.odc.domain.user.User;
import apl.odc.domain.user.repository.UserRepository;
import apl.odc.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User find(Long id) {
        return userRepository.findById(id).orElseThrow(NotFoundException::wrong);
    }

}
