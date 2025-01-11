package com.web.thuvien.repository.custom.impl;

import com.web.thuvien.model.dto.BookDTO;
import com.web.thuvien.model.entity.BookEntity;
import com.web.thuvien.repository.custom.BookRepositoryCustom;
import com.web.thuvien.util.NumberUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    public static void joinTable(StringBuilder sql, BookDTO bookDTO){
    }

    public static void queryNormal(StringBuilder sql, BookDTO bookDTO){
        try{
            Field[] fields = bookDTO.getClass().getDeclaredFields();
            for(Field field: fields){
                field.setAccessible(true);
                String fieldName = field.getName();
                if(!fieldName.equals("id") && !fieldName.equals("images")
                        && !fieldName.equals("files")
                        && !fieldName.equals("likeCount")
                        && !fieldName.equals("types")
                        && !fieldName.equals("description")
                ){
                    Object value = field.get(bookDTO);
                    if(value != null){
                        if(NumberUtil.isNumber(String.valueOf(value))){
                            sql.append(" AND b." + fieldName + " = " + value.toString() + " ");
                        }
                        else {
                            sql.append(" AND b." + fieldName + " LIKE '%" + value.toString() + "%' ");
                        }
                    }
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void querySpecial(StringBuilder sql, BookDTO bookDTO){
        // and b.type like "TRINH_THAM"
        List<String> types = bookDTO.getTypes();
        sql.append(" ( " + types.stream().map(it -> " b.type like '%" + it + "%' ").collect(Collectors.joining(" OR ")) + " ) ");
    }

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<BookEntity> findAll(BookDTO bookDTO) {
        StringBuilder sql = new StringBuilder("SELECT * FROM book b ");
        sql.append(" WHERE 1 = 1 ");
        querySpecial(sql, bookDTO);
        sql.append(" ORDER BY b.id ASC, b.likeCount DESC ");
        Query query = em.createNativeQuery(sql.toString(), BookEntity.class);
        return query.getResultList();
    }
}
