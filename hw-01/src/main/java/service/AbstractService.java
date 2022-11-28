package service;

import java.util.List;

public interface AbstractService<T> {
    List<T> getListObject();
    T getObject(String text);
}
