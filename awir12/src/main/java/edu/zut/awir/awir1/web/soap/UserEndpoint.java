package edu.zut.awir.awir1.web.soap;

import edu.zut.awir.awir1.model.User;
import edu.zut.awir.awir1.service.UserService;

import model.GetUserRequest;
import model.GetUserResponse;
import model.GetUsersResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@RequiredArgsConstructor
public class UserEndpoint {

    private static final String NAMESPACE = "http://zut.edu/awir/users";
    private final UserService userService;

    @PayloadRoot(namespace = NAMESPACE, localPart = "getUserRequest")
    @ResponsePayload
    public GetUserResponse getUser(@RequestPayload GetUserRequest request) {
        GetUserResponse resp = new GetUserResponse();
        User u = userService.findById(request.getId());
        if (u != null) {
            resp.setUser(toUserType(u));
        }
        return resp;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "getUsersRequest")
    @ResponsePayload
    public GetUsersResponse getUsers(@RequestPayload Object request) {
        GetUsersResponse resp = new GetUsersResponse();
        userService.findAll().forEach(u -> resp.getUsers().add(toUserType(u)));
        return resp;
    }

    private model.User toUserType(edu.zut.awir.awir1.model.User u) {
        model.User t = new model.User();
        t.setId(u.getId());
        t.setName(u.getName());
        t.setEmail(u.getEmail());
        return t;
    }
}
