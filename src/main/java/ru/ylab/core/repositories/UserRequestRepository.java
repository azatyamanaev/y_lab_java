package ru.ylab.core.repositories;

import java.util.List;

import ru.ylab.core.models.UserRequest;

/**
 * Interface describing logic for working with user request storage.
 *
 * @author azatyamanaev
 */
public interface UserRequestRepository {

    /**
     * Gets user requests by user id.
     *
     * @param id user id
     * @return list of user requests
     */
    List<UserRequest> getAllForUser(Long id);

    /**
     * Saves user request to storage.
     *
     * @param userRequest user request data
     * @return whether save is successful
     */
    boolean save(UserRequest userRequest);
}
