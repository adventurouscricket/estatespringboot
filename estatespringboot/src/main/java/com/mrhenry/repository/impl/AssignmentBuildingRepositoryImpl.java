package com.mrhenry.repository.impl;

import com.mrhenry.entity.UserEntity;
import com.mrhenry.repository.AssignmentBuildingRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AssignmentBuildingRepositoryImpl implements AssignmentBuildingRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UserEntity> findAllByBuildingId(Long buildingId, Pageable pageable) {
        try {
            StringBuilder sql = new StringBuilder(queryFindAll(buildingId));
            Query query = entityManager.createNativeQuery(sql.toString(), UserEntity.class);
            if (pageable != null) {
                query.setFirstResult((int) pageable.getOffset());
                query.setMaxResults(pageable.getPageSize());
            }
            return query.getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>();
    }

    @Override
    public Long countAllBuildingId(Long buildingId) {
        try {
            StringBuilder sql = new StringBuilder("SELECT COUNT(asb.id) ");
            sql.append(" FROM(");
            sql.append(queryFindAll(buildingId));
            sql.append(") AS asb");
            Query query = entityManager.createNativeQuery(sql.toString());

            List<BigInteger> resultList = query.getResultList();
            return Long.parseLong(resultList.get(0).toString(), 10);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private StringBuilder queryFindAll(Long id) {
        StringBuilder sql = new StringBuilder("SELECT u.id, u.createdby, u.createddate, u.modifiedby, u.modifieddate, u.fullname, u.username, u.password, u.status, assbd.buildingid ");
        sql.append(" FROM user u INNER JOIN userrole ur ON(u.id = ur.userid AND u.status = 1) ");
        sql.append("INNER JOIN role r ON(ur.roleid = r.id AND r.code='staff') ");
        sql.append("LEFT JOIN assignmentbuilding assbd ON (ur.userid = assbd.userid AND assbd.buildingid = "+id+ ") group by u.id");
        return sql;
    }
}
