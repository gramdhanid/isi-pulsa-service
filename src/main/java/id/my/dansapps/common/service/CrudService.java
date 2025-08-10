package id.my.dansapps.common.service;

import id.my.dansapps.common.exception.CustomException;

public interface CrudService <T, ID> {
    T createUpdate(T dto) throws CustomException;
    String delete(ID id) throws CustomException;
    T get(ID id) throws CustomException;
}
