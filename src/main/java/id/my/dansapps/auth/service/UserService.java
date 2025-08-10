package id.my.dansapps.auth.service;

import id.my.dansapps.auth.dto.UserDTO;
import id.my.dansapps.auth.model.User;
import id.my.dansapps.common.exception.CustomException;
import id.my.dansapps.common.service.CrudService;

public interface UserService extends CrudService<UserDTO, Long> {

}
