package ru.ylab.auditstarter.repository;

import java.time.Instant;

/**
 * Interface describing logic for auditing user http requests.
 *
 * @author azatyamanaev
 */
public interface AuditRepository {

    /**
     * Saves user http request to storage.
     *
     * @param method Http request method
     * @param uri Http request uri
     * @param userId user id
     * @param role user role
     * @param timestamp when request was executed
     */
    void save(String method, String uri, Long userId, Object role, Instant timestamp);
}
