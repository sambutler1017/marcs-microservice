package com.marcs.app.user.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.marcs.app.user.client.domain.UserStatus;
import com.marcs.app.user.service.ManageUserStatusService;
import com.marcs.app.user.service.UserStatusService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/user-app/user-status")
@RestController
public class UserStatusController {

    @Autowired
    private UserStatusService userStatusService;

    @Autowired
    private ManageUserStatusService manageUserStatusService;

    /**
     * Gets the status for the given user id.
     * 
     * @param id The id of the user to get the status for.
     * @return {@link UserStatus} object
     * @throws Exception
     */
    @GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    public UserStatus getUserStatusById(@PathVariable int id) throws Exception {
        return userStatusService.getUserStatusById(id);
    }

    /**
     * Inserts the given user status object into the db
     * 
     * @param userStatus Object to be inserted.
     * @return {@link UserStatus} object
     * @throws Exception
     */
    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public UserStatus insertUserStatus(@RequestBody UserStatus userStatus) throws Exception {
        return manageUserStatusService.insertUserStatus(userStatus);
    }

    /**
     * Updates a users status for the given user id
     * 
     * @param id The id of the user to get the status for.
     * @return {@link UserStatus} object
     * @throws Exception
     */
    @PutMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    public UserStatus updateUserStatusByUserId(@PathVariable int id, @RequestBody UserStatus userStatus)
            throws Exception {
        return manageUserStatusService.updateUserStatusByUserId(id, userStatus);
    }

    /**
     * Updates a users app access for the given user id
     * 
     * @param id        The id of the user to get the status for.
     * @param appAccess boolean determining what access the user has.
     * @return {@link UserStatus} object
     * @throws Exception
     */
    @PutMapping(path = "/{id}/access/{appAccess}", produces = APPLICATION_JSON_VALUE)
    public UserStatus updateUserAppAccessByUserId(@PathVariable int id, @PathVariable Boolean appAccess)
            throws Exception {
        return manageUserStatusService.updateUserAppAccessByUserId(id, appAccess);
    }
}
