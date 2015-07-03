package library.utils.security.securedata;

import java.lang.reflect.Type;

interface Parser {

    <T> T fromJson(String content, Type type) throws Exception;

    String toJson(Object body);
    
}
