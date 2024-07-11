package com.mgmtp.cfu.specification;

import com.mgmtp.cfu.entity.Course;
import com.mgmtp.cfu.entity.Registration;
import com.mgmtp.cfu.enums.CourseStatus;
import com.mgmtp.cfu.enums.RegistrationStatus;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class CourseSpecifications {
    public static Specification<Course> nameLike(String name) {
        return (root, query, builder) -> builder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Course> hasStatus(CourseStatus status) {
        return (root, query, builder) -> builder.equal(root.get("status"), status);
    }

    public static Specification<Course> sortByCreatedDateDesc() {
        return (root, query, builder) -> {
            query.orderBy(builder.desc(root.get("createdDate")));
            return query.getRestriction();
        };
    }

    public static Specification<Course> sortByEnrollmentCountDesc(List<RegistrationStatus> acceptedStatuses) {
        return (root, query, cb) -> {
            // Join course and registration
            Subquery<Long> enrollmentCountSubquery = query.subquery(Long.class);
            Root<Registration> registrationSubqueryRoot = enrollmentCountSubquery.from(Registration.class);
            Join<Registration, Course> courseSubqueryJoin = registrationSubqueryRoot.join("course");

            enrollmentCountSubquery.correlate(root);

            // Case expression for counting accepted registrations
            Expression<Long> caseExpression = cb.sum(cb.<Long>selectCase()
                    .when(registrationSubqueryRoot.get("status").in(acceptedStatuses), 1L)
                    .otherwise(0L)
            );

            enrollmentCountSubquery.select(caseExpression)
                    .where(cb.equal(courseSubqueryJoin.get("id"), root.get("id")));

            // Main quẻy
            Predicate statusPredicate = cb.equal(root.get("status"), CourseStatus.AVAILABLE);

            query.orderBy(cb.desc(enrollmentCountSubquery));

            return statusPredicate;
        };
    }
}
