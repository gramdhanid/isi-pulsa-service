package id.my.dansapps.auth.service.impl;

import id.my.dansapps.auth.dto.UserDTO;
import id.my.dansapps.auth.model.User;
import id.my.dansapps.auth.repository.UserRepository;
import id.my.dansapps.auth.service.UserService;
import id.my.dansapps.common.exception.CustomException;
import id.my.dansapps.common.exception.ErrorCode;
import id.my.dansapps.common.util.messages.ErrorMessages;
import id.my.dansapps.common.util.messages.SuccessMessages;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserImpl implements UserService {

    private final UserRepository userRepository;
    private final Argon2PasswordEncoder passwordEncoder;

    public UserImpl(UserRepository userRepository, Argon2PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO createUpdate(UserDTO dto) throws CustomException {
        try {
            Optional<User> userOpt = userRepository.findByUsername(dto.getUsername());
            User user = new User();
            if (userOpt.isPresent()) {
                if (dto.getId() != null){
                    user = userOpt.get();
                } else {
                    throw new CustomException("Username isn't available", ErrorCode.GENERIC_FAILURE);
                }
            }
            user.setUsername(dto.getUsername());
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            user.setFullName(dto.getFullName());
            user.setPhoneNumber(dto.getPhoneNumber());
            user.setAddress(dto.getAddress());
            //user.setPhotoProfile();
            return toDTO(userRepository.save(user));
        } catch (Exception e){
            throw new CustomException(e.getMessage(), ErrorCode.GENERIC_FAILURE);
        }
    }

    @Override
    public String delete(Long aLong) throws CustomException {
        User userOpt = userRepository.findById(aLong)
                .orElseThrow(() -> new CustomException(ErrorMessages.USER_NOT_FOUND, ErrorCode.GENERIC_FAILURE));
        userRepository.delete(userOpt);
        return SuccessMessages.SUCCESS_DELETED + " For User ID " + aLong;
    }


    @Override
    public UserDTO get(Long aLong) throws CustomException {
        User userOpt = userRepository.findById(aLong)
                .orElseThrow(() -> new CustomException(ErrorMessages.USER_NOT_FOUND, ErrorCode.GENERIC_FAILURE));
        return toDTO(userOpt);
    }

    public UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setPassword("secret");
        dto.setUsername(user.getUsername());
        dto.setFullName(user.getFullName());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setAddress(user.getAddress());
        return dto;
    }
}
